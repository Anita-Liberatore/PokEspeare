package com.pokespeare.client;

import com.pokespeare.client.dto.TranslationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FunTranslationsClientImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FunTranslationsClientImpl funTranslationsClient;

    void setUp() {
        ReflectionTestUtils.setField(funTranslationsClient, "baseUrl",
                "https://api.funtranslations.mercxry.me/v1/translate");
    }

    @Test
    void shouldTranslateToYoda() {
        setUp();
        TranslationResponse response = new TranslationResponse(
                new TranslationResponse.Contents("Translated to yoda, this was.")
        );
        when(restTemplate.postForObject(
                eq("https://api.funtranslations.mercxry.me/v1/translate/yoda"),
                any(HttpEntity.class),
                eq(TranslationResponse.class)))
                .thenReturn(response);

        String result = funTranslationsClient.toYoda("This was translated to yoda.");

        assertThat(result).isEqualTo("Translated to yoda, this was.");
    }

    @Test
    void shouldTranslateToShakespeare() {
        setUp();
        TranslationResponse response = new TranslationResponse(
                new TranslationResponse.Contents("Hark, this be the translation.")
        );
        when(restTemplate.postForObject(
                eq("https://api.funtranslations.mercxry.me/v1/translate/shakespeare"),
                any(HttpEntity.class),
                eq(TranslationResponse.class)))
                .thenReturn(response);

        String result = funTranslationsClient.toShakespeare("This is the translation.");

        assertThat(result).isEqualTo("Hark, this be the translation.");
    }
}
