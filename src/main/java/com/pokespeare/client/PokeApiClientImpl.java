package com.pokespeare.client;

import com.pokespeare.client.dto.SpeciesResponse;
import com.pokespeare.exception.PokemonNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PokeApiClientImpl implements PokeApiClient {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon-species/";

    private final RestTemplate restTemplate;

    public PokeApiClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public SpeciesResponse getSpecies(String name) {
        try {
            return restTemplate.getForObject(BASE_URL + name.toLowerCase(), SpeciesResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new PokemonNotFoundException(name);
        }
    }
}
