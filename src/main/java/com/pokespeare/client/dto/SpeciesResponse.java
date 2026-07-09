package com.pokespeare.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SpeciesResponse(
        String name,
        @JsonProperty("is_legendary") boolean isLegendary,
        Habitat habitat,
        @JsonProperty("flavor_text_entries") List<FlavorTextEntry> flavorTextEntries
) {
    public record Habitat(String name) {}

    public record FlavorTextEntry(
            @JsonProperty("flavor_text") String flavorText,
            Language language
    ) {}

    public record Language(String name) {}
}
