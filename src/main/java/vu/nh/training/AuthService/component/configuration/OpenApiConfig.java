package vu.nh.training.AuthService.component.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI OpenApi() {

        return new OpenAPI().info(
            new Info().title("API-service authentication and authorization")
            .version("v1")
            .description("Show url API")
            .license(new License().name("API license").url("http://domain/license"))
        ).servers(List.of(new Server().url("http://localhost:8080").description("Server is developing")))
        ;

    } 
}
