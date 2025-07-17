package com.icaro.freitas.iecommerce_products.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;
import com.icaro.freitas.iecommerce_products.dto.ProductCreateDto;
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

	public static Product fromDto(ProductDto productDto) {

		List<CategoryDto> categoryDtos = productDto.getCategories();

		Set<Category> categories = categoryDtos.stream().map(CategoryMapper::fromDto).collect(Collectors.toSet());

		return new Product(productDto.getId(), productDto.getName(), productDto.getDescription(), productDto.getPrice(),
				productDto.getQuantity(), productDto.getImageUrl(), productDto.getSlug(), productDto.getActive(),
				categories);
	}

	public static Product fromCreateDto(ProductCreateDto productCreateDto, Set<Category> categories) {

		return new Product(null, productCreateDto.getName(), productCreateDto.getDescription(),
				productCreateDto.getPrice(), productCreateDto.getQuantity(), productCreateDto.getImageUrl(),
				productCreateDto.getSlug(), productCreateDto.getActive(), categories);
	}

}
