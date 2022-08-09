package com.grglucastr.homeincapi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Slf4j
@Configuration
public class CorsConfiguration {

    private static final String HOMEINCAPI_ALLOWED_SERVERS = "HOMEINCAPI_ALLOWED_SERVERS";

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                String[] allowedOrigins = getAllowedOrigins();

                registry.addMapping("/**").allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD");

            }
        };
    }

    private String[] getAllowedOrigins(){
        String[] allowedOrigins = new String[]{"http://localhost", "http://localhost:3000", "http://44.198.99.95:3000"};

        final String envVar = System.getenv(HOMEINCAPI_ALLOWED_SERVERS);
        log.info("Reading CORS servers in env variable " + HOMEINCAPI_ALLOWED_SERVERS);
        if(StringUtils.isNotBlank(envVar)){
            log.info("Servers found in env variable");
            final String[] servers = envVar.split(",");
            Arrays.stream(servers).forEach(log::info);
            return servers;
        }

        log.info("Empty env variable: {}", HOMEINCAPI_ALLOWED_SERVERS);
        log.info("Applying default servers values");
        return allowedOrigins;
    }
}
