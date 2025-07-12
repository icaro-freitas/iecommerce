package com.icaro.freitas.iecommerce_products.service.it;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.dto.ProductFilterDto;
import com.icaro.freitas.iecommerce_products.exception.ResourceNotFoundException;
import com.icaro.freitas.iecommerce_products.service.IProductService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private IProductService service;

	@Test
	public void findAllShouldReturnProductDtoPage() {
		final int productsListExpectedLength = 8;
		final Long firstProductExpectedId = 1L;
		final String firstProductExpectedName = "Notebook asus vivobook";

		PageRequest pageRequest = PageRequest.of(0, productsListExpectedLength);

		ProductFilterDto filterDto = new ProductFilterDto();

		Page<ProductDto> result = service.findAll(filterDto, pageRequest);

		List<ProductDto> productList = result.getContent();
		ProductDto firstProduct = productList.get(0);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(productsListExpectedLength, result.getSize());
		Assertions.assertEquals(productsListExpectedLength, result.getTotalElements());
		Assertions.assertEquals(firstProductExpectedId, firstProduct.getId());
		Assertions.assertEquals(firstProductExpectedName, firstProduct.getName());
	}

	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {

		PageRequest pageRequest = PageRequest.of(50, 10);

		ProductFilterDto filterDto = new ProductFilterDto();

		Page<ProductDto> result = service.findAll(filterDto, pageRequest);

		Assertions.assertTrue(result.isEmpty());

	}

	@ParameterizedTest
	@MethodSource("orderProvider")
	void findAllshouldReturnExpectedProductDtoPageWithGivenOrder(Sort sort, String expectedName) {
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

		ProductFilterDto filterDto = new ProductFilterDto();

		Page<ProductDto> result = service.findAll(filterDto, pageRequest);

		List<ProductDto> productList = result.getContent();
		ProductDto firstProduct = productList.get(0);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(expectedName, firstProduct.getName());
	}

	@ParameterizedTest
	@MethodSource("filterProvider")
	void findAllshouldReturnExpectedProductDtoWithGivenFilter(ProductFilterDto dto, int expectedSize, Long expectedId,
			String expectedName) {
		assertFilteredResult(dto, expectedSize, expectedId, expectedName);
	}

	private static Stream<Arguments> filterProvider() {
		return Stream.of(Arguments.of(ProductFilterDto.builder().name("asus").build(), 1, 1L, "Notebook asus vivobook"),
				Arguments.of(ProductFilterDto.builder().description("ASUS").build(), 1, 1L, "Notebook asus vivobook"),
				Arguments.of(ProductFilterDto.builder().minPrice(new BigDecimal("5000.00")).build(), 1, 5L,
						"Geladeira Philco"),
				Arguments.of(ProductFilterDto.builder().maxPrice(new BigDecimal("41.57")).build(), 1, 8L,
						"Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of(ProductFilterDto.builder().minQuantity(195).build(), 1, 3L, "Halter 4kg"),
				Arguments.of(ProductFilterDto.builder().maxQuantity(31).build(), 1, 5L, "Geladeira Philco"),
				Arguments.of(ProductFilterDto.builder().active(true).build(), 7, 1L, "Notebook asus vivobook"),
				Arguments.of(ProductFilterDto.builder().active(false).build(), 1, 8L,
						"Livro Harry Potter e a Pedra Filosofal"),
				Arguments.of(ProductFilterDto.builder().categoryIds(List.of(3L)).build(), 1, 3L, "Halter 4kg"));
	}

	private void assertFilteredResult(ProductFilterDto filterDto, int expectedSize, Long expectedId,
			String expectedName) {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<ProductDto> result = service.findAll(filterDto, pageRequest);

		List<ProductDto> productList = result.getContent();
		ProductDto firstProduct = productList.get(0);

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(expectedSize, productList.size());
		Assertions.assertEquals(expectedId, firstProduct.getId());
		Assertions.assertEquals(expectedName, firstProduct.getName());
	}
	
	@Test
	public void findByIdShouldReturnProductDtoWhenIdExists() {
		Long existingProductId = 1L;
		ProductDto result = service.findById(existingProductId);

		Assertions.assertNotNull(existingProductId);
		Assertions.assertEquals(result.getId(), existingProductId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Long nonExistingProductId = 99L;
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingProductId);
		});
	}

}
