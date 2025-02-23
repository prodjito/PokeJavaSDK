package main.java;

import java.util.List;

import main.java.api.PokeApiClient;
import main.java.api.PokeApiException;
import main.java.model.Generation;
import main.java.model.Pokemon;
import main.java.service.GenerationService;
import main.java.service.PokemonService;

public class Main {

	public static void main(String[] args) {
		PokeApiClient pokeApiClient = new PokeApiClient();
		
		PokemonService pokemonService = new PokemonService(pokeApiClient);
		
		GenerationService generationService = new GenerationService(pokeApiClient);
		
		try {
			
			Pokemon pokemon5 = pokemonService.getPokemonByIdOrName("5");
			System.out.println("Pokemon by ID: " + pokemon5);
			
			Pokemon pikachu = pokemonService.getPokemonByIdOrName("pikachu");
			System.out.println("Pokemon by name: " + pikachu);
			
			List<Pokemon> pokemons = pokemonService.getAllPokemons();
			System.out.println("Retrieved pokemons:");
			pokemons.forEach(System.out::println);
			
			Generation generation3 = generationService.getGenerationByIdOrName("3");
			System.out.println("Generation by ID: " + generation3);
			
			Generation generationi = generationService.getGenerationByIdOrName("generation-i");
			System.out.println("Generation by name: " + generationi);
			
			List<Generation> generations = generationService.getAllGenerations();
			System.out.println("Retrieved generations:");
			generations.forEach(System.out::println);
			
		}catch(PokeApiException e) {
			System.err.println("Error fetching data: " + e.getMessage());
			e.printStackTrace();
		}
		

	}

}
