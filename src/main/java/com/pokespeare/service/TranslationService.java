package com.pokespeare.service;

import com.pokespeare.client.FunTranslationsClient;
import com.pokespeare.client.PokeApiClient;
import com.pokespeare.client.dto.SpeciesResponse;
import com.pokespeare.dto.PokemonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);

    private final PokeApiClient pokeApiClient;
    private final FunTranslationsClient funTranslationsClient;

    public TranslationService(PokeApiClient pokeApiClient, FunTranslationsClient funTranslationsClient) {
        this.pokeApiClient = pokeApiClient;
        this.funTranslationsClient = funTranslationsClient;
    }

    @Cacheable("translations")
    public PokemonResponse getTranslated(String name) {
        SpeciesResponse species = pokeApiClient.getSpecies(name);
        String description = extractEnglishDescription(species);

        String translated = attemptTranslation(description, species);
        return buildResponse(species, translated);
    }

    private String extractEnglishDescription(SpeciesResponse species) {
        return species.flavorTextEntries().stream()
                .filter(e -> "en".equals(e.language().name()))
                .map(SpeciesResponse.FlavorTextEntry::flavorText)
                .map(text -> text.replace("\n", " ").replace("\f", " "))
                .findFirst()
                .orElse("");
    }

    private String attemptTranslation(String description, SpeciesResponse species) {
        boolean useYoda = species.isLegendary() || "cave".equals(habitatName(species));
        try {
            return useYoda
                    ? funTranslationsClient.toYoda(description)
                    : funTranslationsClient.toShakespeare(description);
        } catch (Exception e) {
            log.warn("Translation failed, falling back to standard description: {}", e.getMessage());
            return description;
        }
    }

    private String habitatName(SpeciesResponse species) {
        return species.habitat() != null ? species.habitat().name() : null;
    }

    private PokemonResponse buildResponse(SpeciesResponse species, String description) {
        return new PokemonResponse(
                species.name(),
                description,
                habitatName(species),
                species.isLegendary()
        );
    }
}
