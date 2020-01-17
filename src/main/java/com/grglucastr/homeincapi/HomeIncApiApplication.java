package com.grglucastr.homeincapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class HomeIncApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeIncApiApplication.class, args);
	}

	//TODO: Add swagger configuration
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.grglucastr.homeincapi.controller"))
				.paths(PathSelectors.any())
				.build();
	}

}
