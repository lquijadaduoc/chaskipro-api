package com.chaskipro.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI chaskiproOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("Chaskipro API")
                        .description("API REST para marketplace de servicios profesionales en Chile. " +
                                "Permite registro de usuarios, gestión de perfiles profesionales, " +
                                "búsqueda de profesionales por comuna y sistema de calificaciones.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Chaskipro Team")
                                .email("admin@chaskipro.com")
                                .url("https://github.com/lquijadaduoc/chaskipro-api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://api.chaskipro.com")
                                .description("Servidor de Producción")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingrese el token JWT obtenido del endpoint /auth/login")));
    }
}
