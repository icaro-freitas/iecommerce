package com.icaro.freitas.iecommerce_products.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.dto.ProductFilterDto;
import com.icaro.freitas.iecommerce_products.entity.Product;
import com.icaro.freitas.iecommerce_products.mapper.ProductMapper;
import com.icaro.freitas.iecommerce_products.service.IProductService;
import com.icaro.freitas.iecommerce_products.testutil.ProductFactory;


@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class ProductControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private IProductService service;	
	
	private Page<ProductDto> productDtoPage;	

	@BeforeEach
	void setUp() throws Exception {
		
		List<Product> productList = ProductFactory.createProductList();
		
		List<ProductDto> productDtoList = productList.stream().map(ProductMapper::toDto).toList();
		
		productDtoPage = new PageImpl<>(productDtoList); 		

		when(service.findAll(any(ProductFilterDto.class),any(Pageable.class))).thenReturn(productDtoPage);
		
	}
	
	@Test
	public void findAllShouldReturnProductDtoPage() throws Exception {

		final Integer productsListExpectedLength = 3;
		final Long firstProductExpectedId = 1L;
		final String firstProductExpectedName = "Notebook asus vivobook";
		final BigDecimal firstProductExpectedPrice = new BigDecimal("2867.0");
		final String firstProductExpectedImageUrl = "img/notebook-asus-vivobook";
		final String firstProductExpectedFirstCategoryName = "Eletrônicos";

		ResultActions result = mockMvc.perform(get("/api/v1/products").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		result.andExpect(jsonPath("$.content[0].price").value(firstProductExpectedPrice));
		result.andExpect(jsonPath("$.content[0].imageUrl").value(firstProductExpectedImageUrl));
		result.andExpect(jsonPath("$.content[0].categories[0].name").value(firstProductExpectedFirstCategoryName));
	}	

}
