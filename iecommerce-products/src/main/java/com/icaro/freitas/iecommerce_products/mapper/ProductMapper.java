package com.icaro.freitas.iecommerce_products.mapper;

import java.util.List;

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;
import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.entity.Product;

public class ProductMapper {
	
	public static ProductDto toDto(Product product) {
		
		List<CategoryDto> categoryDTOs = product.getCategories().stream()
		        .map(c -> CategoryMapper.toDto(c))
		        .toList();

		    return new ProductDto(
		        product.getId(),
		        product.getName(),
		        product.getDescription(),
		        product.getPrice(),
		        product.getQuantity(),
		        product.getImageUrl(),
		        product.getSlug(),
		        product.getActive(),
		        categoryDTOs
		    );
	}

}
