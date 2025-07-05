package com.icaro.freitas.iecommerce_products.specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.icaro.freitas.iecommerce_products.entity.Product;
import com.icaro.freitas.iecommerce_products.repository.ProductRepository;

@DataJpaTest
public class ProductSpecificationsTests {

	@Autowired
	private ProductRepository repository;

	@ParameterizedTest
	@MethodSource("filterProvider")
	void findAllshouldReturnExpectedProductWithGivenFilter(Specification<Product> spec, int expectedSize,
			Long expectedId, String expectedName) {
		assertFilteredResult(spec, expectedSize, expectedId, expectedName);
	}

	private static Stream<Arguments> filterProvider() {
		return Stream.of(Arguments.of(ProductSpecifications.nameContains("asus"), 1, 1L, "Notebook asus vivobook"),
				Arguments.of(ProductSpecifications.descriptionContains("ASUS"), 1, 1L, "Notebook asus vivobook"),
				Arguments.of(ProductSpecifications.minPrice(new BigDecimal("5000.00")), 1, 5L, "Geladeira Philco"),
				Arguments.of(ProductSpecifications.maxPrice(new BigDecimal("41.57")), 1, 8L,
						"Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of(ProductSpecifications.minQuantity(195), 1, 3L, "Halter 4kg"),
				Arguments.of(ProductSpecifications.maxQuantity(31), 1, 5L, "Geladeira Philco"),
				Arguments.of(ProductSpecifications.isActive(true), 7, 1L, "Notebook asus vivobook"),
				Arguments.of(ProductSpecifications.isActive(false), 1, 8L, "Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of(ProductSpecifications.categoryIdIn(List.of(3L)), 1, 3L, "Halter 4kg"));
	}

	private void assertFilteredResult(Specification<Product> spec, int expectedSize, Long expectedId,
			String expectedName) {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Product> result = repository.findAll(spec, pageRequest);

		List<Product> productList = result.getContent();
		Product firstProduct = productList.get(0);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(expectedSize, productList.size());
		Assertions.assertEquals(expectedId, firstProduct.getId());
		Assertions.assertEquals(expectedName, firstProduct.getName());
	}

}
