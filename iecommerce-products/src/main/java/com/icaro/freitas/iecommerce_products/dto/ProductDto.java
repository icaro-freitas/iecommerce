package com.icaro.freitas.iecommerce_products.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser nulo ou vazio.")
	private String name;
	
	@Size(min = 10, message = "Descrição precisa ter de no mínimo 10 caracteres")
	@NotEmpty(message = "Descrição não pode ser nula ou vazia.")
	private String description;	
	
	@PositiveOrZero(message = "Preço precisa ser zero ou positivo")
	private BigDecimal price;
	
	@PositiveOrZero(message = "Quantidade precisa ser zero ou positiva")
	private Integer quantity;
	
	private String imageUrl;
	
	@NotEmpty(message = "Slug Não pode ser nula ou vazia.")
	private String slug;
	
	@NotNull(message = "Active não pode ser nula")
	private Boolean active;

	@NotEmpty(message = "Deve ter pelo menos uma categoria")
	private List<CategoryDto> categories = new ArrayList<CategoryDto>();

}
