package com.icaro.freitas.iecommerce_products.service;

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
import org.springframework.data.jpa.domain.Specification;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.dto.ProductFilterDto;
import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.entity.Product;
import com.icaro.freitas.iecommerce_products.repository.ProductRepository;
import com.icaro.freitas.iecommerce_products.service.impl.ProductServiceImpl;
import com.icaro.freitas.iecommerce_products.testutil.ProductFactory;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

	@Mock
	private ProductRepository repository;

	@InjectMocks
	private ProductServiceImpl service;

	private List<Product> list;
	private Page<Product> page;

	@SuppressWarnings("unchecked")
	@BeforeEach
	private void setUp() throws Exception {

		list = ProductFactory.createProductList();
		page = new PageImpl<>(list);
		Mockito.when(repository.findAll((Specification<Product>) Mockito.any(Specification.class),
				Mockito.any(Pageable.class))).thenReturn(page);
	}

	@Test
	public void findAllShouldReturnProductDtoPagedList() {
		Pageable pageable = PageRequest.of(0, 10);

		ProductFilterDto filter = new ProductFilterDto();

		Page<ProductDto> result = service.findAll(filter, pageable);
		Product expected = list.get(0);
		ProductDto actual = result.getContent().get(0);
		String actualCategoryName = actual.getCategories().get(0).getName();

		List<String> expectedCategoryNames = expected.getCategories().stream().map(Category::getName).toList();

		Assertions.assertEquals(list.size(), result.getTotalElements(), "Total elements mismatch");
		Assertions.assertEquals(expected.getId(), actual.getId(), "Product ID mismatch");
		Assertions.assertEquals(expected.getName(), actual.getName(), "Product name mismatch");
		Assertions.assertTrue(expectedCategoryNames.contains(actualCategoryName));
	}

}
