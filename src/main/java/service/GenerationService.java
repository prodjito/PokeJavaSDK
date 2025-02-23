package main.java.service;

import java.util.List;

import main.java.api.PokeApiClient;
import main.java.api.PokeApiException;
import main.java.model.Generation;

public class GenerationService {
	
	private static final String BASE_URL = "https://pokeapi.co/api/v2/generation";
	
	private final PokeApiClient pokeApiClient;
	
	public GenerationService(PokeApiClient pokeApiClient) {
		this.pokeApiClient = pokeApiClient;
	}
	
	public Generation getGenerationByIdOrName(String idOrName) throws PokeApiException {
		return pokeApiClient.getByIdOrName(BASE_URL + "/" + idOrName, Generation.class);
	}
	
	public List<Generation> getAllGenerations() throws PokeApiException {
		return pokeApiClient.getList(BASE_URL,  Generation.class);
	}

}
