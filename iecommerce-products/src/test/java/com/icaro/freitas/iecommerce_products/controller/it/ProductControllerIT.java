package com.icaro.freitas.iecommerce_products.controller.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
	public void findAllShouldReturnProductDtoPage() throws Exception {

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
	
	@ParameterizedTest
	@MethodSource("orderProvider")
	void findAllshouldReturnExpectedProductDtoPageWithGivenOrder(String paramName, String paramValue, Long expectedId, String expectedName) throws Exception{
		assertOrderedResult(paramName, paramValue, expectedId,expectedName);
	}
	
	private static Stream<Arguments> orderProvider() {
		return Stream.of(Arguments.of("sort", "name,asc", 2L,"As Crônicas de Nárnia"),
				Arguments.of("sort", "description,asc", 8L,"Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of("sort", "price,asc", 8L,"Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of("sort", "quantity,asc", 5L,"Geladeira Philco"));
	}
	
	private void assertOrderedResult(String paramName, String paramValue, Long expectedId, String expectedName) throws Exception {

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param(paramName, paramValue).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(expectedId));
		result.andExpect(jsonPath("$.content[0].name").value(expectedName));
	}
	
	@Test
	public void findAllShouldReturnPageSortedByActiveWhenSpecified() throws Exception {

		final Long firstProductExpectedId = 8L;
		final String firstProductExpectedName = "Livro Harry Potter e a Pedra Filosofal";

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param("sort", "active,asc").param("sort","name,desc").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(firstProductExpectedId));
		result.andExpect(jsonPath("$.content[0].name").value(firstProductExpectedName));
	}


	@Test
	public void findAllShouldReturnBadRequestWhenInvalidSortParamIsGiven() throws Exception {
		mockMvc.perform(get("/api/v1/products").param("sort", "unknownField,asc")).andExpect(status().isBadRequest());
	}

	@ParameterizedTest
	@MethodSource("filterProvider")
	void findAllshouldReturnExpectedProductDtoWithGivenFilter(String paramName, String paramValue, int expectedSize,
			Long expectedId, String expectedName) throws Exception {
		assertFilteredResult(paramName, paramValue, expectedSize, expectedId, expectedName);
	}

	private static Stream<Arguments> filterProvider() {
		return Stream.of(Arguments.of("name", "asus", 1, 1L, "Notebook asus vivobook"),
				Arguments.of("description", "ASUS", 1, 1L, "Notebook asus vivobook"),
				Arguments.of("minPrice", "5000.00", 1, 5L, "Geladeira Philco"),
				Arguments.of("maxPrice", "41.57", 1, 8L, "Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of("minQuantity", "195", 1, 3L, "Halter 4kg"),
				Arguments.of("maxQuantity", "31", 1, 5L, "Geladeira Philco"),
				Arguments.of("active", "true", 7, 1L, "Notebook asus vivobook"),
				Arguments.of("active", "false", 1, 8L, "Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of("categoryIds", "3", 1, 3L, "Halter 4kg"));
	}

	private void assertFilteredResult(String paramName, String paramValue, int expectedSize, Long expectedId,
			String expectedName) throws Exception {

		ResultActions result = mockMvc
				.perform(get("/api/v1/products").param(paramName, paramValue).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content.length()").value(expectedSize));
		result.andExpect(jsonPath("$.content[0].id").value(expectedId));
		result.andExpect(jsonPath("$.content[0].name").value(expectedName));
	}

}
