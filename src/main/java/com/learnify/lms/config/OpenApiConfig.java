package com.learnify.lms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI learnifyOpenApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("API Documentation for lms-core-service")
                .description("REST API documentation lms-core-service")
                .version("v1")
                .contact(new Contact().name("Learnify Team")));
  }
}
