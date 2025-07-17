package com.icaro.freitas.iecommerce_products.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ProductCreateDto {
	
	@NotEmpty(message = "Name can not be null or empty")
	@Schema(description = "Product name", example = "Vivo notebook")
	private String name;
	
	@Size(min = 10, message = "Description need to have at least 10 characters")
	@NotEmpty(message = "Description can not be null or empty")
	@Schema(description = "Product description", example = "É leve. É compacto. É o ASUS Vivobook Go 15. Projetado para torná-lo produtivo e mantê-lo entretido onde quer que você vá!")
	private String description;	
	
	@PositiveOrZero(message = "Price must be zero or positive")
	@Schema(description = "Product price", example = "2870.33")
	private BigDecimal price;
	
	@PositiveOrZero(message = "Quantity must be zero or positive")
	@Schema(description = "Product quantity", example = "50")
	private Integer quantity;
	
	@Schema(description = "Product image Url", example = "img/notebook-asus-vivobook")
	private String imageUrl;
	
	
	@NotEmpty(message = "Slug can not be null or empty")
	@Schema(description = "Product slug", example = "notebook-asus-vivobook")
	private String slug;
	
	@NotNull(message = "Active can not be null")
	@Schema(description = "If the product is active for visualization", example = "true")
	private Boolean active;

	@NotEmpty(message = "At least one category id is needed")
	@Schema(description = "Product category ids", example = "[1,2]")
	private List<Long> categoryIds = new ArrayList<Long>();

}
