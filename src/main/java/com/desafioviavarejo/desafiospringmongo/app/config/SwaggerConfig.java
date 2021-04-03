package com.desafioviavarejo.desafiospringmongo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String PASTA_SWAGGER = "com.desafioviavarejo.desafiospringmongo.app.entrypoint";

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("desafioAPI")
                .select()
                .apis(RequestHandlerSelectors.basePackage(PASTA_SWAGGER))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(dados());
    }

    private ApiInfo dados() {
        return new ApiInfoBuilder()
                .title("Spring Boot REST API")
                .description("Projeto desafio Via Varejo")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Paulo Abreu", "sem url", "paullooabreu@gmail.com"))
                .build();
    }
}
