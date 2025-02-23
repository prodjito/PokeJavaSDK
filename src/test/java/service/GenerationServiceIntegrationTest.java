package test.java.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.api.PokeApiClient;
import main.java.model.Generation;
import main.java.service.GenerationService;

public class GenerationServiceIntegrationTest {
	private PokeApiClient pokeApiClient;
	private GenerationService generationService;
	
	@BeforeEach
	void setUp() {
		pokeApiClient = new PokeApiClient();
		generationService = new GenerationService(pokeApiClient);
	}
	
	@Test
	void testGetGenerationByIdOrNameIntegration() {
		String testGenerationName = "generation-viii";
		Generation generation = generationService.getGenerationByIdOrName(testGenerationName);
		assertNotNull(generation);
		assertEquals(testGenerationName, generation.getName());
		System.out.println(generation);
		
	}
	
	@Test
	void testGetAllGenerationsIntegration() {
		List<Generation> generations = generationService.getAllGenerations();
		assertNotNull(generations);
		assertFalse(generations.isEmpty());
		System.out.println("Retrieved generations:");
		generations.forEach(System.out::println);
	}
}
