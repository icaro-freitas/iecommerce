package com.icaro.freitas.iecommerce_products.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandardErrorDto {

	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

}
