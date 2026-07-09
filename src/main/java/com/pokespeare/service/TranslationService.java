package com.pokespeare.service;

import com.pokespeare.client.FunTranslationsClient;
import com.pokespeare.dto.PokemonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);

    private final PokemonService pokemonService;
    private final FunTranslationsClient funTranslationsClient;

    public TranslationService(PokemonService pokemonService, FunTranslationsClient funTranslationsClient) {
        this.pokemonService = pokemonService;
        this.funTranslationsClient = funTranslationsClient;
    }

    @Cacheable("translations")
    public PokemonResponse getTranslated(String name) {
        PokemonResponse pokemon = pokemonService.getPokemon(name);
        String translated = attemptTranslation(pokemon);
        return new PokemonResponse(pokemon.name(), translated, pokemon.habitat(), pokemon.isLegendary());
    }

    private String attemptTranslation(PokemonResponse pokemon) {
        boolean useYoda = pokemon.isLegendary() || "cave".equals(pokemon.habitat());
        try {
            return useYoda
                    ? funTranslationsClient.toYoda(pokemon.description())
                    : funTranslationsClient.toShakespeare(pokemon.description());
        } catch (Exception e) {
            log.warn("Translation failed, falling back to standard description: {}", e.getMessage());
            return pokemon.description();
        }
    }
}
