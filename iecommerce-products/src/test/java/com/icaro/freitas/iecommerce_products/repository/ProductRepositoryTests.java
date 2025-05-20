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

import com.icaro.freitas.iecommerce_products.entity.Product;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	@Test
    void findAllshouldReturnProductsInPages() {
        Pageable pageable = PageRequest.of(0, 2); 
        Page<Product> page = repository.findAll(pageable);

        assertEquals(2, page.getContent().size());
        assertEquals(4, page.getTotalPages());
        assertEquals(8, page.getTotalElements());
    }

    @Test
    void findAllshouldReturnProductsOrderedByNameAscWhenSpecified() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
        Page<Product> page = repository.findAll(pageable);
        List<Product> products = page.getContent();
        List<String> productNames = products.stream()
                                     .map(Product::getName)
                                     .toList();

        assertEquals(List.of("As Crônicas de Nárnia", "Ducha Jet", "Geladeira Philco", "Halter 4kg", "Livro Harry Potter e a Pedra Filosofal"), productNames);
    }
    
    @Test
    void findAllshouldReturnProductsOrderedByDescriptionAscWhenSpecified() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("description").ascending());
        Page<Product> page = repository.findAll(pageable);
        List<Product> products = page.getContent();
        List<String> productNames = products.stream()
                                     .map(Product::getName)
                                     .toList();

        assertEquals(List.of("Livro Harry Potter e a Pedra Filosofal", "As Crônicas de Nárnia", "Ducha Jet", "Geladeira Philco", "Samsung Galaxy A35"), productNames);
    }

    @Test
    void findAllshouldReturnProductsOrderedByPriceAscWhenSpecified() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("price").ascending());
        Page<Product> page = repository.findAll(pageable);
        List<Product> products = page.getContent();
        List<String> productNames = products.stream()
                .map(Product::getName)
                .toList();

        assertEquals(List.of("Livro Harry Potter e a Pedra Filosofal", "As Crônicas de Nárnia", "Halter 4kg", "Torradeira Elétrica", "Ducha Jet"), productNames);
    }
    
    @Test
    void findAllshouldReturnProductsOrderedByQuantityAscWhenSpecified() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("quantity").ascending());
        Page<Product> page = repository.findAll(pageable);
        List<Product> products = page.getContent();
        List<String> productNames = products.stream()
                .map(Product::getName)
                .toList();

        assertEquals(List.of("Geladeira Philco", "Notebook asus vivobook", "Torradeira Elétrica", "Samsung Galaxy A35", "As Crônicas de Nárnia"), productNames);
    }
    
    @Test
    void findAllshouldReturnProductsOrderedByActiveAscWhenSpecified() {
        Pageable pageable = PageRequest.of(0, 5,  Sort.by(
                Sort.Order.asc("active"),
                Sort.Order.desc("name")
            ));
        Page<Product> page = repository.findAll(pageable);
        List<Product> products = page.getContent();
        List<String> productNames = products.stream()
                .map(Product::getName)
                .toList();

        assertEquals(List.of("Livro Harry Potter e a Pedra Filosofal", "Torradeira Elétrica", "Samsung Galaxy A35", "Notebook asus vivobook", "Halter 4kg"), productNames);
    }

}
