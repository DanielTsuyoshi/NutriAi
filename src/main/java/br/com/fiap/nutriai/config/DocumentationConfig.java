package br.com.fiap.nutriai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class DocumentationConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API da NutriAI")
                .contact(new Contact()
                    .email("nutriai@gmail.com.br")
                    .name("NutriAI")
                )
                .version("v1")
                .description("Uma API para nutrição e saúde")
            )
            .components(new Components()
            .addSecuritySchemes("bearer-key",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                .bearerFormat("JWT")));
    }
}
