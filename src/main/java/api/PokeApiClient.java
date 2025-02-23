package main.java.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class PokeApiClient {
	
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	private static final int MAX_RETRIES = 3;
	private static final int RETRY_DELAY_MS = 1000;
	
	public PokeApiClient() {
		this.httpClient = HttpClient.newHttpClient();
		this.objectMapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
	}
	
	public <T> T getByIdOrName(String url, Class<T> type) throws PokeApiException {
		try {
			HttpResponse<String> response = sendWithRetry(url);
			
			return objectMapper.readValue(response.body(), type);
		} catch(IOException | InterruptedException e) {
			throw new PokeApiException("Failed to retrieve data from: " + url, e);
		}
	}
	
	public <T> List<T> getList(String baseUrl, Class<T> fullType) throws PokeApiException {
		try {
			//use cursor-based (nextUrl) pagination to retrieve all partial objects
			List<Map<String, Object>> allPartialObjects = new ArrayList<>();
			String nextUrl = baseUrl;
			
			while (nextUrl != null) {
				
				HttpResponse<String> response = sendWithRetry(nextUrl);
				
				JsonNode rootNode = objectMapper.readTree(response.body());
				List<Map<String, Object>> partialList = objectMapper.convertValue(
						rootNode.get("results"), 
						new TypeReference<List<Map<String, Object>>>(){}
				);
				
				allPartialObjects.addAll(partialList);
				nextUrl = rootNode.path("next").isNull() ? null : rootNode.get("next").asText();
			}
			
			//Sequential retrieval of the full objects is very slow
			/*List<T> allFullObjects = new ArrayList<>();
			for (Map<String,Object> partialObject : allPartialObjects) {
				String name = (String) partialObject.get("name");
				System.out.println(name);
				allFullObjects.add(getByIdOrName(baseUrl + "/" + name, fullType));
			}
			
			return allFullObjects;*/
			
			//Asynchronous retrieval of the full objects is much faster
			ExecutorService executor = Executors.newFixedThreadPool(10);
			try {
				List<CompletableFuture<T>> futures = allPartialObjects.stream()
						.map(partialObject -> CompletableFuture.supplyAsync(() -> {
							String name = (String) partialObject.get("name");
							return getByIdOrName(baseUrl + "/" + name, fullType);
						}, executor))
						.collect(Collectors.toList());
				
				return futures.stream()
						.map(CompletableFuture::join)
						.collect(Collectors.toList());
			} finally {
				executor.shutdown();
			}
			
		} catch( IOException | InterruptedException e) {
			throw new PokeApiException("Failed to retrieve paginated list from: " + baseUrl, e);
		}
	}
	
	private HttpResponse<String> sendWithRetry(String url) throws IOException, InterruptedException {
		int attempts = 0;
		while (attempts < MAX_RETRIES) {
			try {
				HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(url))
						.GET().build();
				HttpResponse<String> response = httpClient.send(request,  HttpResponse.BodyHandlers.ofString());
				if(response.statusCode() == 200) {
					return response;
				}
 			} catch(IOException | InterruptedException e ) {
 				if(attempts == MAX_RETRIES - 1) {
 					throw e;
 				}
 				Thread.sleep(RETRY_DELAY_MS);
 			}
			attempts++;
		}
		throw new PokeApiException("Failed to retrieve data after " + MAX_RETRIES + "attempts.");
	}
	
}
