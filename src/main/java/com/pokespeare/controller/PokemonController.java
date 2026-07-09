package com.pokespeare.controller;

import com.pokespeare.dto.PokemonResponse;
import com.pokespeare.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
@Tag(name = "Pokemon", description = "Retrieve Pokémon information")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Get Pokémon by name",
            description = "Returns basic information about a Pokémon including its English description, habitat and legendary status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pokémon found",
                            content = @Content(schema = @Schema(implementation = PokemonResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pokémon not found", content = @Content)
            }
    )
    public ResponseEntity<PokemonResponse> getPokemon(
            @Parameter(description = "Pokémon name (e.g. mewtwo)", example = "mewtwo")
            @PathVariable String name) {
        return ResponseEntity.ok(pokemonService.getPokemon(name));
    }
}
