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

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
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

	@BeforeEach
	private void setUp() throws Exception {

		list = ProductFactory.createProductList();
		page = new PageImpl<>(list);
		Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
	}

	@Test
	public void findAllShouldReturnProductDtoPagedList() {

		Pageable pageable = PageRequest.of(0, 10);

		Page<ProductDto> result = service.findAll(pageable);

		Assertions.assertEquals(result.getTotalElements(), list.size());
		Assertions.assertEquals(result.getContent().get(0).getId(), list.get(0).getId());
		Assertions.assertEquals(result.getContent().get(0).getName(), list.get(0).getName());
	}	

}
