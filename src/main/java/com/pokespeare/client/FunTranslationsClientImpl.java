package com.pokespeare.client;

import com.pokespeare.client.dto.TranslationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class FunTranslationsClientImpl implements FunTranslationsClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public FunTranslationsClientImpl(RestTemplate restTemplate,
                                     @Value("${funtranslations.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public String toYoda(String text) {
        return translate(text, "yoda");
    }

    @Override
    public String toShakespeare(String text) {
        return translate(text, "shakespeare");
    }

    private String translate(String text, String translator) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("text", text), headers);
        TranslationResponse response = restTemplate.postForObject(
                baseUrl + "/" + translator, request, TranslationResponse.class);

        if (response == null || response.contents() == null) {
            throw new RuntimeException("Empty response from FunTranslations");
        }
        return response.contents().translated();
    }
}
