package com.pokespeare.service;

import com.pokespeare.client.FunTranslationsClient;
import com.pokespeare.dto.PokemonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @Mock
    private PokemonService pokemonService;

    @Mock
    private FunTranslationsClient funTranslationsClient;

    @InjectMocks
    private TranslationService translationService;

    private PokemonResponse pokemon(String name, String habitat, boolean legendary) {
        return new PokemonResponse(name, "Standard description.", habitat, legendary);
    }

    @Test
    void shouldUseYodaTranslationForLegendaryPokemon() {
        when(pokemonService.getPokemon("mewtwo")).thenReturn(pokemon("mewtwo", "rare", true));
        when(funTranslationsClient.toYoda("Standard description.")).thenReturn("Yoda text, this is.");

        PokemonResponse result = translationService.getTranslated("mewtwo");

        assertThat(result.description()).isEqualTo("Yoda text, this is.");
        verify(funTranslationsClient).toYoda(anyString());
        verify(funTranslationsClient, never()).toShakespeare(anyString());
    }

    @Test
    void shouldUseYodaTranslationForCaveHabitatPokemon() {
        when(pokemonService.getPokemon("zubat")).thenReturn(pokemon("zubat", "cave", false));
        when(funTranslationsClient.toYoda("Standard description.")).thenReturn("In cave, lives it.");

        PokemonResponse result = translationService.getTranslated("zubat");

        assertThat(result.description()).isEqualTo("In cave, lives it.");
        verify(funTranslationsClient).toYoda(anyString());
        verify(funTranslationsClient, never()).toShakespeare(anyString());
    }

    @Test
    void shouldUseShakespeareTranslationForRegularPokemon() {
        when(pokemonService.getPokemon("pikachu")).thenReturn(pokemon("pikachu", "forest", false));
        when(funTranslationsClient.toShakespeare("Standard description.")).thenReturn("Shakespearean text.");

        PokemonResponse result = translationService.getTranslated("pikachu");

        assertThat(result.description()).isEqualTo("Shakespearean text.");
        verify(funTranslationsClient).toShakespeare(anyString());
        verify(funTranslationsClient, never()).toYoda(anyString());
    }

    @Test
    void shouldFallbackToStandardDescriptionWhenTranslationFails() {
        when(pokemonService.getPokemon("pikachu")).thenReturn(pokemon("pikachu", "forest", false));
        when(funTranslationsClient.toShakespeare(anyString())).thenThrow(new RuntimeException("rate limited"));

        PokemonResponse result = translationService.getTranslated("pikachu");

        assertThat(result.description()).isEqualTo("Standard description.");
    }

    @Test
    void shouldFallbackWhenYodaTranslationFails() {
        when(pokemonService.getPokemon("mewtwo")).thenReturn(pokemon("mewtwo", "rare", true));
        when(funTranslationsClient.toYoda(anyString())).thenThrow(new RuntimeException("rate limited"));

        PokemonResponse result = translationService.getTranslated("mewtwo");

        assertThat(result.description()).isEqualTo("Standard description.");
    }
}
