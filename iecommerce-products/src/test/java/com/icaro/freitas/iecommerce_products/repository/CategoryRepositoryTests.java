package com.icaro.freitas.iecommerce_products.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.icaro.freitas.iecommerce_products.entity.Category;

@DataJpaTest
public class CategoryRepositoryTests {

	@Autowired
	private CategoryRepository repository;

	@Test
	void findAllShouldReturnCategoriesInPages() {
	    Pageable pageable = PageRequest.of(0, 2);
	    Page<Category> page = repository.findAll(pageable);

	    List<Category> categories = page.getContent();

	    assertEquals(2, categories.size(), "Expected 2 categories on the first page");
	    assertEquals(2, page.getTotalPages(), "Expected total of 2 pages");
	    assertEquals(4, page.getTotalElements(), "Expected total of 4 categories");
	}

	@Test
	void findAllshouldReturnCategoriesOrderedByNameAscWhenSpecified() {
		Pageable pageable = PageRequest.of(0, 4, Sort.by("name").ascending());
		Page<Category> page = repository.findAll(pageable);
		List<Category> categories = page.getContent();
		List<String> expectedCategoryNames = List.of("Eletrodomésticos", "Eletrônicos", "Esportes", "Livros");
		List<String> actualCategoryNames = categories.stream().map(Category::getName).toList();

		assertEquals(expectedCategoryNames, actualCategoryNames, "Categories are not sorted by name ascending as expected");
	}

}
