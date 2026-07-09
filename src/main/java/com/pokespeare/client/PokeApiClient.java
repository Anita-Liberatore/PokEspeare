package com.pokespeare.client;

import com.pokespeare.client.dto.SpeciesResponse;

public interface PokeApiClient {
    SpeciesResponse getSpecies(String name);
}
