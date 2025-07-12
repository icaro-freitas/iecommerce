package com.icaro.freitas.iecommerce_products.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.entity.Product;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	@Test
	void findAllShouldReturnProductsInPages() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Product> page = repository.findAll(pageable);

		assertEquals(2, page.getContent().size(), "Expected 2 products on the first page");
		assertEquals(4, page.getTotalPages(), "Expected total of 4 pages");
		assertEquals(8, page.getTotalElements(), "Expected total of 8 products");
	}

	@Test
	void findAllShouldReturnEmptyProductPage() {
		Pageable pageable = PageRequest.of(50, 10);
		Page<Product> page = repository.findAll(pageable);

		List<Product> products = page.getContent();
		assertTrue(products.isEmpty(), "Product page should be empty");
	}

	@Test
	void findAllShouldReturnProductsWithCategories() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Product> page = repository.findAll(pageable);

		List<Product> products = page.getContent();
		assertFalse(products.isEmpty(), "Product page should not be empty");

		Set<Category> categories = products.get(0).getCategories();
		assertFalse(categories.isEmpty(), "First product should have at least one category");

		assertTrue(categories.contains(new Category(1L, "Eletrônicos")),
				"The first product should contain the 'Eletrônicos' category");
	}

	@ParameterizedTest
	@MethodSource("orderProvider")
	void findAllshouldReturnExpectedProductPageWithGivenOrder(Sort sort, String expectedName) {
		assertOrderedResult(sort, expectedName);
	}

	private static Stream<Arguments> orderProvider() {
		return Stream.of(Arguments.of(Sort.by("name"), "As Crônicas de Nárnia"),
				Arguments.of(Sort.by("description"), "Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of(Sort.by("price"), "Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of(Sort.by("quantity"), "Geladeira Philco"),
				Arguments.of(Sort.by(Sort.Order.asc("active"), Sort.Order.desc("name")),
						"Livro Harry Potter e a Pedra Filosofal"));
	}

	private void assertOrderedResult(Sort sort, String expectedName) {

		PageRequest pageRequest = PageRequest.of(0, 10, sort);

		Page<Product> result = repository.findAll(pageRequest);

		List<Product> productList = result.getContent();
		Product firstProduct = productList.get(0);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(expectedName, firstProduct.getName());
	}

	@Test
	public void findByIdShouldReturnProductDtoWhenIdExists() {
		Long existingProductId = 1L;
		Optional<Product> result = repository.findById(existingProductId);

		Product product = result.get();

		Assertions.assertNotNull(existingProductId);
		Assertions.assertEquals(product.getId(), existingProductId);
	}

	@Test
	public void findByIdShouldReturnEmptyOptionalResultWhenIdDoesNotExist() {
		Long nonExistingProductId = 99L;
		Optional<Product> result = repository.findById(nonExistingProductId);

		Assertions.assertTrue(result.isEmpty());

	}

}
