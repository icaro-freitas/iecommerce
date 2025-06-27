package com.icaro.freitas.iecommerce_products.specification;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.entity.Product;
import com.icaro.freitas.iecommerce_products.repository.ProductRepository;
import com.icaro.freitas.iecommerce_products.testutil.ProductFactory;

@DataJpaTest
public class ProductSpecificationsTests {

	@Autowired
	private ProductRepository repository;

	private Product product;

	@BeforeEach
	private void setUp() throws Exception {
		product = ProductFactory.createProduct();
	}

	@Test
	void nameContainsShouldReturnMatchingProduct() {
		var spec = ProductSpecifications.nameContains("asus");
		List<Product> result = repository.findAll(spec);
		assertThat(result).containsOnly(product);
	}

	@Test
	void descriptionContainsShouldReturnMatchingProduct() {
		var spec = ProductSpecifications.descriptionContains("ASUS");
		List<Product> result = repository.findAll(spec);
		assertThat(result).containsOnly(product);
	}

	@Test
	void categoryIdInShouldReturnProductInCategory() {
		Long categoryId = 1L;
		var spec = ProductSpecifications.categoryIdIn(List.of(categoryId));
		List<Product> result = repository.findAll(spec);
		assertThat(result)
				.allSatisfy(p -> assertThat(p.getCategories()).extracting(Category::getId).contains(categoryId));
	}

	@Test
	void minPriceShouldReturnProductsWithGreaterOrEqualPrice() {
		var testPrice = new BigDecimal("2000");
		var spec = ProductSpecifications.minPrice(testPrice);
		List<Product> result = repository.findAll(spec);
		assertThat(result).allSatisfy(p -> assertThat(p.getPrice()).isGreaterThanOrEqualTo(testPrice));
	}

	@Test
	void maxPriceShouldReturnProductsWithLessOrEqualPrice() {
		var testPrice = new BigDecimal("3000");
		var spec = ProductSpecifications.maxPrice(testPrice);
		List<Product> result = repository.findAll(spec);
		assertThat(result).allSatisfy(p -> assertThat(p.getPrice()).isLessThanOrEqualTo(testPrice));
	}

	@Test
	void minQuantityShouldReturnProductsWithSufficientQuantity() {
		var testQuantity = 20;
		var spec = ProductSpecifications.minQuantity(testQuantity);
		List<Product> result = repository.findAll(spec);
		assertThat(result).allSatisfy(p -> assertThat(p.getQuantity()).isGreaterThanOrEqualTo(testQuantity));
	}

	@Test
	void maxQuantityShouldReturnProductsWithLowQuantity() {
		var testQuantity = 51;
		var spec = ProductSpecifications.maxQuantity(testQuantity);
		List<Product> result = repository.findAll(spec);
		assertThat(result).allSatisfy(p -> assertThat(p.getQuantity()).isLessThanOrEqualTo(testQuantity));
	}

	@Test
	void isActive_shouldReturnActiveProductsOnly() {
		var activeValue = true;
		var spec = ProductSpecifications.isActive(activeValue);
		List<Product> result = repository.findAll(spec);
		assertThat(result).allSatisfy(p -> assertThat(p.getActive()).isEqualTo(activeValue));
	}

	@Test
	void isActiveShouldReturnInactiveProductsOnly() {
		var inactiveValue = false;
		var spec = ProductSpecifications.isActive(inactiveValue);
		List<Product> result = repository.findAll(spec);
		assertThat(result).allSatisfy(p -> assertThat(p.getActive()).isEqualTo(inactiveValue));
	}

}
