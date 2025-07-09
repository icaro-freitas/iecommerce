package com.icaro.freitas.iecommerce_products.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;
import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.entity.Product;

public class ProductMapper {

	public static ProductDto toDto(Product product) {

		Set<Category> categories = product.getCategories();

		List<CategoryDto> categoryDtos = categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());

		return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
				product.getQuantity(), product.getImageUrl(), product.getSlug(), product.getActive(), categoryDtos);
	}

}
