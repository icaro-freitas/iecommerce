package com.icaro.freitas.iecommerce_products.service.it;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;
import com.icaro.freitas.iecommerce_products.service.ICategoryService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class CategoryServiceIT {
	
	@Autowired
	private ICategoryService service;
	
	@Test
	public void findAllShouldReturnCategoryDtoPage() {
		final int categoryListExpectedLength = 4;
		final Long firstCategoryExpectedId = 1L;
		final String firstCategoryExpectedName = "Eletrônicos";

		PageRequest pageRequest = PageRequest.of(0, categoryListExpectedLength);		

		Page<CategoryDto> result = service.findAll(pageRequest);

		List<CategoryDto> categoryList = result.getContent();
		CategoryDto firstCategory = categoryList.get(0);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(categoryListExpectedLength, result.getSize());
		Assertions.assertEquals(categoryListExpectedLength, result.getTotalElements());
		Assertions.assertEquals(firstCategoryExpectedId, firstCategory.getId());
		Assertions.assertEquals(firstCategoryExpectedName, firstCategory.getName());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {

		PageRequest pageRequest = PageRequest.of(50, 10);
		

		Page<CategoryDto> result = service.findAll(pageRequest);

		Assertions.assertTrue(result.isEmpty());

	}
	
	@Test
	public void findAllShouldReturnCategoryDtoPageSortedByNameWhenSpecified() {
		
		String expectedName = "Eletrodomésticos";

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));		

		Page<CategoryDto> result = service.findAll(pageRequest);

		List<CategoryDto> categoryList = result.getContent();
		CategoryDto firstCategory = categoryList.get(0);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(expectedName, firstCategory.getName());
	}

}
