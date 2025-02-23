package main.java.service;

import java.util.List;

import main.java.api.PokeApiClient;
import main.java.api.PokeApiException;
import main.java.model.Pokemon;

public class PokemonService {
	
	public static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon";
	
	private final PokeApiClient pokeApiClient;
	
	public PokemonService(PokeApiClient pokeApiClient) {
		this.pokeApiClient = pokeApiClient;
	}
	
	public Pokemon getPokemonByIdOrName(String idOrName) throws PokeApiException {
		return pokeApiClient.getByIdOrName(BASE_URL + "/" + idOrName, Pokemon.class);
	}
	
	public List<Pokemon> getAllPokemons() throws PokeApiException {
		return pokeApiClient.getList(BASE_URL,  Pokemon.class);
	}
}
