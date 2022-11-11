package com.grglucastr.homeincapi.configurations;

import com.grglucastr.homeincapi.models.Cors;
import com.grglucastr.homeincapi.repositories.CorsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class CorsConfiguration {

    @Autowired
    private CorsRepository corsRepository;

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                final List<Cors> cors = corsRepository.findAll();

                if(cors.isEmpty())
                    return;

                final String[] allowedOrigings = cors
                        .stream()
                        .map(c -> {
                            log.info("CORS Allowed origins: {}", c.getAllowedOrigin());
                            return c.getAllowedOrigin();
                        })
                        .toArray(String[]::new);

                final String[] allowedMethods = {"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"};

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigings)
                        .allowedMethods(allowedMethods);

                WebMvcConfigurer.super.addCorsMappings(registry);
            }
        };
    }
}
