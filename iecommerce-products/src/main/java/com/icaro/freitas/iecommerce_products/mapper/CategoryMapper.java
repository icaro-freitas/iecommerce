package com.icaro.freitas.iecommerce_products.mapper;

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;
import com.icaro.freitas.iecommerce_products.entity.Category;

public class CategoryMapper {

	public static CategoryDto toDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		return categoryDto;
	}

	public static Category fromDto(CategoryDto categoryDto) {
		Category category = new Category();
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		return category;
	}

}
