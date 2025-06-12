package com.icaro.freitas.iecommerce_products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(
        name = "Categories",
        description = "Schema to hold Category information"
)
public class CategoryDto {

	@Schema(description = "Category id", example = "1")
	private Long id;
	@Schema(description = "Category name", example = "Eletrônicos")
	@NotEmpty(message = "Name can not be null or empty")
	private String name;

}
