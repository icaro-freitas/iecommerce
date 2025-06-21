package com.icaro.freitas.iecommerce_products.controller.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void findAllShouldReturnPage() throws Exception {

		final Integer productsListExpectedLength = 8;
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

	@Test
	public void findAllShouldReturnPageSortedByNameWhenSpecified() throws Exception {

		final Long firstProductExpectedId = 2L;
		final String firstProductExpectedName = "As Crônicas de Nárnia";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("sort", "name,asc").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
	}

	@Test
	public void findAllShouldReturnBadRequestWhenInvalidSortParamIsGiven() throws Exception {
		mockMvc.perform(get("/api/v1/products").param("sort", "unknownField,asc")).andExpect(status().isBadRequest());
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByGivenName() throws Exception {
		
		final Integer productsListExpectedLength = 1;
		final Long firstProductExpectedId = 1L;
		final String firstProductExpectedName = "Notebook asus vivobook";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("name", "asus").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByGivenDescription() throws Exception {
		
		final Integer productsListExpectedLength = 1;
		final Long firstProductExpectedId = 1L;
		final String firstProductExpectedName = "Notebook asus vivobook";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("description", "ASUS").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByGivenMinPrice() throws Exception {
		
		final Integer productsListExpectedLength = 1;
		final Long firstProductExpectedId = 5L;
		final String firstProductExpectedName = "Geladeira Philco";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("minPrice", "5000.00").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByGivenMaxPrice() throws Exception {
		
		final Integer productsListExpectedLength = 1;
		final Long firstProductExpectedId = 8L;
		final String firstProductExpectedName = "Livro Harry Potter e a Pedra Filosofal";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("maxPrice", "41.57").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByGivenMinQuantity() throws Exception {
		
		final Integer productsListExpectedLength = 1;
		final Long firstProductExpectedId = 3L;
		final String firstProductExpectedName = "Halter 4kg";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("minQuantity", "195").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByGivenMaxQuantity() throws Exception {
		
		final Integer productsListExpectedLength = 1;
		final Long firstProductExpectedId = 5L;
		final String firstProductExpectedName = "Geladeira Philco";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("maxQuantity", "31").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByActiveParameterWithTrueValue() throws Exception {
		
		final Integer productsListExpectedLength = 7;
		final Long firstProductExpectedId = 1L;
		final String firstProductExpectedName = "Notebook asus vivobook";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("active", "true").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByActiveParameterWithFalseValue() throws Exception {
		
		final Integer productsListExpectedLength = 1;
		final Long firstProductExpectedId = 8L;
		final String firstProductExpectedName = "Livro Harry Potter e a Pedra Filosofal";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("active", "false").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}
	
	@Test
	public void findAllShouldReturnPageFilteredByGivenCategories() throws Exception {
		
		final Integer productsListExpectedLength = 1;
		final Long firstProductExpectedId = 3L;
		final String firstProductExpectedName = "Halter 4kg";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("categoryIds", "3").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());		
		result.andExpect(jsonPath("$.content.length()").value(productsListExpectedLength));
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
		
	}

}
