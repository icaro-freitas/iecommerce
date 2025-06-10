package com.icaro.freitas.iecommerce_products.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.service.IProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/products", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
public class ProductController {	
	
	private IProductService service;
	
	@GetMapping
	public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable) {
		
		Page<ProductDto> products = service.findAll(pageable);
		
		return ResponseEntity.ok(products);
		
	}

}
