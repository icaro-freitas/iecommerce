package com.icaro.freitas.iecommerce_products.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;
import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.repository.CategoryRepository;
import com.icaro.freitas.iecommerce_products.service.impl.CategoryServiceImpl;
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
	public void findAllShouldReturnCategoryDtoPagedList() {
	    Pageable pageable = PageRequest.of(0, 10);
	    Page<CategoryDto> result = service.findAll(pageable);
	    
	    assertEquals(list.size(), result.getTotalElements(), "Total number of elements mismatch");

	    Category expected = list.get(0);
	    CategoryDto actual = result.getContent().get(0);

	    assertEquals(expected.getId(), actual.getId(), "Category ID mismatch");
	    assertEquals(expected.getName(), actual.getName(), "Category name mismatch");
	}

}
