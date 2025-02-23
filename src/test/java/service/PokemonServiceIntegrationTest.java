package test.java.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.api.PokeApiClient;
import main.java.model.Pokemon;
import main.java.service.PokemonService;


public class PokemonServiceIntegrationTest {
	
	private PokeApiClient pokeApiClient;
	private PokemonService pokemonService;
	
	@BeforeEach
	void setUp() {
		pokeApiClient = new PokeApiClient();
		pokemonService = new PokemonService(pokeApiClient);
	}
	
	@Test
	void testGetPokemonByIdOrNameIntegration() {
		String testPokemonName = "pikachu";
		Pokemon pokemon = pokemonService.getPokemonByIdOrName(testPokemonName);
		assertNotNull(pokemon);
		assertEquals(testPokemonName, pokemon.getName());
		System.out.println(pokemon);
		
	}
	
	
	@Test
	void testGetAllPokemonIntegration() {
		List<Pokemon> pokemons = pokemonService.getAllPokemons();
		assertNotNull(pokemons);
		assertFalse(pokemons.isEmpty());
		System.out.println("Retrieved pokemons:");
		pokemons.forEach(System.out::println);
	}
	
}
