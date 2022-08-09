package com.grglucastr.homeincapi.configuration;

import com.sun.xml.fastinfoset.util.StringArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Configuration
public class CorsConfiguration {

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

        try {
            final File file = ResourceUtils.getFile("classpath:static/allowed-servers.txt");
            final List<String> servers = Files.readAllLines(Path.of(file.getPath()));
            log.info("Found allowed servers files! (allowed-servers.txt)");
            log.info("Listing all allowed servers:");
            servers.forEach(log::info);

            return servers.toArray(String[]::new);

        } catch (FileNotFoundException e) {
            log.error("Servers file (resources/static/allowed-servers.txt) not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allowedOrigins;
    }
}
