package com.icaro.freitas.iecommerce_products.dto;

import java.math.BigDecimal;
import java.util.List;

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
public class ProductFilterDto {
	private String name;
	private String description;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private Integer minQuantity;
	private Integer maxQuantity;
	private Boolean active;
	private List<Long> categoryIds;

}
