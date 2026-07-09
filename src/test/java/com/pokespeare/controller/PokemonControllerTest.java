package com.pokespeare.controller;

import com.pokespeare.dto.PokemonResponse;
import com.pokespeare.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PokemonController.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @Test
    void getPokemon_shouldReturnPokemonResponse() throws Exception {
        PokemonResponse response = new PokemonResponse(
                "mewtwo",
                "It was created by a scientist after years of horrific gene splicing.",
                "rare",
                true
        );
        when(pokemonService.getPokemon("mewtwo")).thenReturn(response);

        mockMvc.perform(get("/pokemon/mewtwo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mewtwo"))
                .andExpect(jsonPath("$.description").value("It was created by a scientist after years of horrific gene splicing."))
                .andExpect(jsonPath("$.habitat").value("rare"))
                .andExpect(jsonPath("$.isLegendary").value(true));
    }

    @Test
    void getPokemon_whenNotFound_shouldReturn404() throws Exception {
        when(pokemonService.getPokemon("unknown")).thenThrow(new com.pokespeare.exception.PokemonNotFoundException("unknown"));

        mockMvc.perform(get("/pokemon/unknown"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
