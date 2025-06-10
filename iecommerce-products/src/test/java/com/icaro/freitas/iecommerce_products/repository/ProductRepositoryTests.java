package com.icaro.freitas.iecommerce_products.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
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

	@Test
	void findAllShouldReturnProductsOrderedByNameAscWhenSpecified() {
	    Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
	    Page<Product> page = repository.findAll(pageable);

	    List<String> productNames = page.getContent().stream()
	        .map(Product::getName)
	        .toList();

	    assertEquals(
	        List.of("As Crônicas de Nárnia", "Ducha Jet", "Geladeira Philco", "Halter 4kg",
	                "Livro Harry Potter e a Pedra Filosofal"),
	        productNames,
	        "Products should be ordered by name ascending"
	    );
	}

	@Test
	void findAllShouldReturnProductsOrderedByDescriptionAscWhenSpecified() {
	    Pageable pageable = PageRequest.of(0, 5, Sort.by("description").ascending());
	    Page<Product> page = repository.findAll(pageable);

	    List<String> productNames = page.getContent().stream()
	        .map(Product::getName)
	        .toList();

	    assertEquals(
	        List.of("Livro Harry Potter e a Pedra Filosofal", "As Crônicas de Nárnia", "Ducha Jet",
	                "Geladeira Philco", "Samsung Galaxy A35"),
	        productNames,
	        "Products should be ordered by description ascending"
	    );
	}

	@Test
	void findAllShouldReturnProductsOrderedByPriceAscWhenSpecified() {
	    Pageable pageable = PageRequest.of(0, 5, Sort.by("price").ascending());
	    Page<Product> page = repository.findAll(pageable);

	    List<String> productNames = page.getContent().stream()
	        .map(Product::getName)
	        .toList();

	    assertEquals(
	        List.of("Livro Harry Potter e a Pedra Filosofal", "As Crônicas de Nárnia", "Halter 4kg",
	                "Torradeira Elétrica", "Ducha Jet"),
	        productNames,
	        "Products should be ordered by price ascending"
	    );
	}

	@Test
	void findAllShouldReturnProductsOrderedByQuantityAscWhenSpecified() {
	    Pageable pageable = PageRequest.of(0, 5, Sort.by("quantity").ascending());
	    Page<Product> page = repository.findAll(pageable);

	    List<String> productNames = page.getContent().stream()
	        .map(Product::getName)
	        .toList();

	    assertEquals(
	        List.of("Geladeira Philco", "Notebook asus vivobook", "Torradeira Elétrica", "Samsung Galaxy A35",
	                "As Crônicas de Nárnia"),
	        productNames,
	        "Products should be ordered by quantity ascending"
	    );
	}

	@Test
	void findAllShouldReturnProductsOrderedByActiveAscWhenSpecified() {
	    Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("active"), Sort.Order.desc("name")));
	    Page<Product> page = repository.findAll(pageable);

	    List<String> productNames = page.getContent().stream()
	        .map(Product::getName)
	        .toList();

	    assertEquals(
	        List.of("Livro Harry Potter e a Pedra Filosofal", "Torradeira Elétrica", "Samsung Galaxy A35",
	                "Notebook asus vivobook", "Halter 4kg"),
	        productNames,
	        "Products should be ordered by 'active' ascending and then 'name' descending"
	    );
	}

}
