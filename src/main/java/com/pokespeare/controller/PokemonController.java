package com.pokespeare.controller;

import com.pokespeare.dto.PokemonResponse;
import com.pokespeare.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<PokemonResponse> getPokemon(@PathVariable String name) {
        return ResponseEntity.ok(pokemonService.getPokemon(name));
    }
}
