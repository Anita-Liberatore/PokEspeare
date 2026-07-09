package com.pokespeare.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TranslationResponse(Contents contents) {

    public record Contents(@JsonProperty("translated") String translated) {}
}
