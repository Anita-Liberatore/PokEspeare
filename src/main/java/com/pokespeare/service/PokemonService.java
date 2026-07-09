package com.pokespeare.service;

import com.pokespeare.client.PokeApiClient;
import com.pokespeare.client.dto.SpeciesResponse;
import com.pokespeare.dto.PokemonResponse;
import org.springframework.stereotype.Service;

@Service
public class PokemonService {

    private final PokeApiClient pokeApiClient;

    public PokemonService(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }

    public PokemonResponse getPokemon(String name) {
        SpeciesResponse species = pokeApiClient.getSpecies(name);

        String description = species.flavorTextEntries().stream()
                .filter(e -> "en".equals(e.language().name()))
                .map(SpeciesResponse.FlavorTextEntry::flavorText)
                .map(text -> text.replace("\n", " ").replace("\f", " "))
                .findFirst()
                .orElse("");

        return new PokemonResponse(
                species.name(),
                description,
                species.habitat() != null ? species.habitat().name() : null,
                species.isLegendary()
        );
    }
}
