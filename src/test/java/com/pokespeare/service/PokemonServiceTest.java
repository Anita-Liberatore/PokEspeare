package com.pokespeare.service;

import com.pokespeare.client.PokeApiClient;
import com.pokespeare.client.dto.SpeciesResponse;
import com.pokespeare.dto.PokemonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokeApiClient pokeApiClient;

    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        pokemonService = new PokemonService(pokeApiClient);
    }

    @Test
    void getPokemon_shouldMapFieldsCorrectly() {
        SpeciesResponse species = new SpeciesResponse(
                "mewtwo",
                true,
                new SpeciesResponse.Habitat("rare"),
                List.of(
                        new SpeciesResponse.FlavorTextEntry("It was created by a scientist.", new SpeciesResponse.Language("en")),
                        new SpeciesResponse.FlavorTextEntry("Un Pokémon créé par un scientifique.", new SpeciesResponse.Language("fr"))
                )
        );
        when(pokeApiClient.getSpecies("mewtwo")).thenReturn(species);

        PokemonResponse result = pokemonService.getPokemon("mewtwo");

        assertThat(result.name()).isEqualTo("mewtwo");
        assertThat(result.description()).isEqualTo("It was created by a scientist.");
        assertThat(result.habitat()).isEqualTo("rare");
        assertThat(result.isLegendary()).isTrue();
    }

    @Test
    void getPokemon_shouldPickFirstEnglishDescription() {
        SpeciesResponse species = new SpeciesResponse(
                "pikachu",
                false,
                new SpeciesResponse.Habitat("forest"),
                List.of(
                        new SpeciesResponse.FlavorTextEntry("Un Pokémon électrique.", new SpeciesResponse.Language("fr")),
                        new SpeciesResponse.FlavorTextEntry("When it is angered, it immediately discharges the energy.", new SpeciesResponse.Language("en")),
                        new SpeciesResponse.FlavorTextEntry("A second English entry.", new SpeciesResponse.Language("en"))
                )
        );
        when(pokeApiClient.getSpecies("pikachu")).thenReturn(species);

        PokemonResponse result = pokemonService.getPokemon("pikachu");

        assertThat(result.description()).isEqualTo("When it is angered, it immediately discharges the energy.");
    }
}
