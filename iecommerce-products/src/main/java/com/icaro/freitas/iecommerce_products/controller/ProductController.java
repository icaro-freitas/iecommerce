package com.icaro.freitas.iecommerce_products.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.dto.ProductFilterDto;
import com.icaro.freitas.iecommerce_products.dto.StandardErrorDto;
import com.icaro.freitas.iecommerce_products.service.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Product API", description = "REST API endpoints in Iecommerce to CREATE, UPDATE, FETCH, and DELETE product details")
@RestController
@RequestMapping(path = "api/v1/products", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
public class ProductController {

	private IProductService service;

	@Operation(summary = "Fetch Products paged", description = "Fetch a products page with sort and filtering options")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "400", description = "HTTP Status Internal Bad Request", content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = StandardErrorDto.class))) })
	@GetMapping
	public ResponseEntity<Page<ProductDto>> findAll(@ModelAttribute ProductFilterDto filter, Pageable pageable) {

		Page<ProductDto> products = service.findAll(filter, pageable);

		return ResponseEntity.ok(products);

	}

	@Operation(summary = "Fetch product by ID", description = "Fetch a product from the system using its ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
			@ApiResponse(responseCode = "400", description = "HTTP Status Internal Bad Request", content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
			@ApiResponse(responseCode = "404", description = "HTTP Status Internal Not Found", content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = StandardErrorDto.class))) })
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
		ProductDto dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

}
