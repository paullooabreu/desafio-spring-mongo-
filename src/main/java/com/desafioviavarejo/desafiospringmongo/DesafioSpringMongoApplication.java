package com.desafioviavarejo.desafiospringmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DesafioSpringMongoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DesafioSpringMongoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(DesafioSpringMongoApplication.class);
    }

}
