package com.pokespeare.client;

import com.pokespeare.client.dto.SpeciesResponse;
import com.pokespeare.exception.PokemonNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokeApiClientImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokeApiClientImpl pokeApiClient;

    @Test
    void shouldReturnSpeciesResponse() {
        SpeciesResponse expected = new SpeciesResponse(
                "pikachu", false, null,
                List.of(new SpeciesResponse.FlavorTextEntry("A description.", new SpeciesResponse.Language("en")))
        );
        when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon-species/pikachu", SpeciesResponse.class))
                .thenReturn(expected);

        SpeciesResponse result = pokeApiClient.getSpecies("pikachu");

        assertThat(result.name()).isEqualTo("pikachu");
    }

    @Test
    void shouldNormalizePokemonNameToLowerCase() {
        SpeciesResponse expected = new SpeciesResponse(
                "pikachu", false, null, List.of()
        );
        when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon-species/pikachu", SpeciesResponse.class))
                .thenReturn(expected);

        SpeciesResponse result = pokeApiClient.getSpecies("PIKACHU");

        assertThat(result.name()).isEqualTo("pikachu");
    }

    @Test
    void shouldThrowPokemonNotFoundExceptionOn404() {
        when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon-species/unknown", SpeciesResponse.class))
                .thenThrow(HttpClientErrorException.NotFound.class);

        assertThatThrownBy(() -> pokeApiClient.getSpecies("unknown"))
                .isInstanceOf(PokemonNotFoundException.class);
    }
}
