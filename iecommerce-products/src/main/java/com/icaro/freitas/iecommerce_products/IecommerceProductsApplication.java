package com.icaro.freitas.iecommerce_products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Products microservice REST API Documentation",
				description = "Iecommerce Products microservice REST API Documentation",
				version = "v1")
		)
public class IecommerceProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IecommerceProductsApplication.class, args);
	}

}
