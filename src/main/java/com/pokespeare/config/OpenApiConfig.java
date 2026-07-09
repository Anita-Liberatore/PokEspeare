package com.pokespeare.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PokEspeare API")
                        .description("Retrieve Pokémon descriptions — plain or translated into Shakespearean/Yoda style")
                        .version("1.0.0"));
    }
}
