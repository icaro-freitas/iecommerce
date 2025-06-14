package com.icaro.freitas.iecommerce_products.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "StandardError", description = "Schema to hold error information")
public class StandardErrorDto {

	@Schema(description = "Error timestamp of occurrence", example = "2025-06-12 17:40:12.213213459")
	private Instant timestamp;
	@Schema(description = "Error status code", example = "404")
	private Integer status;
	@Schema(description = "Error status type", example = "Not found")
	private String error;
	@Schema(description = "Error message", example = "Resource not found")
	private String message;
	@Schema(description = "Resource path", example = "/api/v1/products")
	private String path;

}
