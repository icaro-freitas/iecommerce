package com.icaro.freitas.iecommerce_products.dto;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(
        name = "ProductFilter",
        description = "Schema to hold Product filter information"
)
public class ProductFilterDto {

	@Schema(description = "Product name for filtering", example = "asus")
	private String name;
	@Schema(description = "Product description for filtering", example = "ASUS")
	private String description;
	@Schema(description = "Product minimum price for filtering", example = "43.33")
	private BigDecimal minPrice;
	@Schema(description = "Product maximum price for filtering", example = "2000.45")
	private BigDecimal maxPrice;
	@Schema(description = "Product minimum quantity for filtering", example = "10")
	private Integer minQuantity;
	@Schema(description = "Product maximum quantity for filtering", example = "70")
	private Integer maxQuantity;
	@Schema(description = "Filter if a Product is active or not", example = "true")
	private Boolean active;
	@Schema(description = "Filter Products using a category id list", example = "1,2,3")
	private List<Long> categoryIds;

}
