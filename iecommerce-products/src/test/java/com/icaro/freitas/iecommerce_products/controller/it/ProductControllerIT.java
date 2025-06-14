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
	public void findAllShouldReturnBadRequestWhenInvalidSortParamIsGiven() throws Exception {
		mockMvc.perform(get("/api/v1/products").param("sort", "unknownField,asc")).andExpect(status().isBadRequest());
	}

}
