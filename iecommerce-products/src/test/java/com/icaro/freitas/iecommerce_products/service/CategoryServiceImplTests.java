package com.icaro.freitas.iecommerce_products.service;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;
import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.impl.CategoryServiceImpl;
import com.icaro.freitas.iecommerce_products.repository.CategoryRepository;
import com.icaro.freitas.iecommerce_products.testutil.CategoryFactory;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTests {

	@Mock
	private CategoryRepository repository;

	@InjectMocks
	private CategoryServiceImpl service;

	private List<Category> list;
	private Page<Category> page;

	@BeforeEach
	private void setUp() throws Exception {

		list = CategoryFactory.createCategoryList();
		page = new PageImpl<>(list);
		Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
	}

	@Test
	public void findAllShouldReturnCategoryDtoList() {

		Pageable pageable = PageRequest.of(0, 10);

		Page<CategoryDto> result = service.findAll(pageable);

		Assertions.assertEquals(result.getTotalElements(), list.size());
		Assertions.assertEquals(result.getContent().get(0).getId(), list.get(0).getId());
		Assertions.assertEquals(result.getContent().get(0).getName(), list.get(0).getName());
	}

	@Test
	void findAllshouldReturnCategoryDtosOrderedByNameAscWhenSpecified() {

		list.sort(Comparator.comparing(Category::getName));

		Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

		page = new PageImpl<>(list);

		Mockito.when(repository.findAll(pageable)).thenReturn(page);

		Page<CategoryDto> result = service.findAll(pageable);

		Assertions.assertEquals(list.get(0).getName(), result.getContent().get(0).getName());
		Assertions.assertEquals(list.get(1).getName(), result.getContent().get(1).getName());
		Assertions.assertEquals(list.get(2).getName(), result.getContent().get(2).getName());
	}

}
