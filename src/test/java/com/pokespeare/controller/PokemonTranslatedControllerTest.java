package com.pokespeare.controller;

import com.pokespeare.dto.PokemonResponse;
import com.pokespeare.exception.PokemonNotFoundException;
import com.pokespeare.service.PokemonService;
import com.pokespeare.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PokemonController.class)
class PokemonTranslatedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokemonService pokemonService;

    @MockitoBean
    private TranslationService translationService;

    @Test
    void shouldReturnTranslatedPokemon() throws Exception {
        when(translationService.getTranslated("mewtwo"))
                .thenReturn(new PokemonResponse("mewtwo", "Yoda translated text", "rare", true));

        mockMvc.perform(get("/pokemon/translated/mewtwo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mewtwo"))
                .andExpect(jsonPath("$.description").value("Yoda translated text"))
                .andExpect(jsonPath("$.habitat").value("rare"))
                .andExpect(jsonPath("$.isLegendary").value(true));
    }

    @Test
    void shouldReturn404WhenPokemonNotFound() throws Exception {
        when(translationService.getTranslated("unknownmon"))
                .thenThrow(new PokemonNotFoundException("unknownmon"));

        mockMvc.perform(get("/pokemon/translated/unknownmon"))
                .andExpect(status().isNotFound());
    }
}
