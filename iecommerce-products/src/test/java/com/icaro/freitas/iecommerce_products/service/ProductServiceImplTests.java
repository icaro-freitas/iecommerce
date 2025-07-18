package com.icaro.freitas.iecommerce_products.service;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.icaro.freitas.iecommerce_products.dto.ProductCreateDto;
import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.dto.ProductFilterDto;
import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.entity.Product;
import com.icaro.freitas.iecommerce_products.exception.ResourceNotFoundException;
import com.icaro.freitas.iecommerce_products.repository.CategoryRepository;
import com.icaro.freitas.iecommerce_products.repository.ProductRepository;
import com.icaro.freitas.iecommerce_products.service.impl.ProductServiceImpl;
import com.icaro.freitas.iecommerce_products.testutil.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceImplTests {

	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private ProductServiceImpl service;
	
	
	private List<Product> list;
	private Page<Product> page;
	private Long existingProductId;
	private Long nonExistingProductId;
	private Product product;	
	private ProductCreateDto productCreateDto;

	@SuppressWarnings("unchecked")
	@BeforeEach
	private void setUp() throws Exception {
		existingProductId = 1L;
		nonExistingProductId = 2L;
		product= ProductFactory.createProduct();

		list = ProductFactory.createProductList();
		page = new PageImpl<>(list);
		
		productCreateDto = ProductFactory.createProductCreateDto();
		
		Mockito.when(repository.findAll((Specification<Product>) Mockito.any(Specification.class),
				Mockito.any(Pageable.class))).thenReturn(page);				
		
		Mockito.when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingProductId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.save(any())).thenReturn(product);
		
		Mockito.when(categoryRepository.findAllById(any())).thenReturn(product.getCategories().stream().toList());
		
	}

	@Test
	public void findAllShouldReturnProductDtoPagedList() {
		Pageable pageable = PageRequest.of(0, 10);

		ProductFilterDto filter = new ProductFilterDto();

		Page<ProductDto> result = service.findAll(filter, pageable);
		Product expected = list.get(0);
		ProductDto actual = result.getContent().get(0);
		String actualCategoryName = actual.getCategories().get(0).getName();

		List<String> expectedCategoryNames = expected.getCategories().stream().map(Category::getName).toList();

		Assertions.assertEquals(list.size(), result.getTotalElements(), "Total elements mismatch");
		Assertions.assertEquals(expected.getId(), actual.getId(), "Product ID mismatch");
		Assertions.assertEquals(expected.getName(), actual.getName(), "Product name mismatch");
		Assertions.assertTrue(expectedCategoryNames.contains(actualCategoryName));
	}
	
	@Test
	public void findByIdShouldReturnProductDtoWhenIdExists() {
		ProductDto result = service.findById(existingProductId);

		Assertions.assertNotNull(existingProductId);
		Assertions.assertEquals(result.getId(), existingProductId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingProductId);
		});
	}
	
	@Test
	public void createProductShouldReturnProductDto() {
		ProductDto result = service.createProduct(productCreateDto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), product.getId());
	}

}
