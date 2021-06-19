package com.grglucastr.homeincapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomeIncApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeIncApiApplication.class, args);
	}

}
