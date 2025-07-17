package com.icaro.freitas.iecommerce_products.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icaro.freitas.iecommerce_products.dto.ProductCreateDto;
import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.dto.ProductFilterDto;
import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.entity.Product;
import com.icaro.freitas.iecommerce_products.exception.ResourceNotFoundException;
import com.icaro.freitas.iecommerce_products.mapper.ProductMapper;
import com.icaro.freitas.iecommerce_products.repository.CategoryRepository;
import com.icaro.freitas.iecommerce_products.repository.ProductRepository;
import com.icaro.freitas.iecommerce_products.service.IProductService;
import com.icaro.freitas.iecommerce_products.specification.ProductSpecifications;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

	private ProductRepository repository;
	
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<ProductDto> findAll(ProductFilterDto filter, Pageable pageable) {
		Specification<Product> spec = Specification.where(ProductSpecifications.nameContains(filter.getName()))
				.and(ProductSpecifications.descriptionContains(filter.getDescription()))
				.and(ProductSpecifications.minPrice(filter.getMinPrice()))
				.and(ProductSpecifications.maxPrice(filter.getMaxPrice()))
				.and(ProductSpecifications.minQuantity(filter.getMinQuantity()))
				.and(ProductSpecifications.maxQuantity(filter.getMaxQuantity()))
				.and(ProductSpecifications.isActive(filter.getActive()))
				.and(ProductSpecifications.categoryIdIn(filter.getCategoryIds()));

		Page<Product> page = repository.findAll(spec, pageable);
		return page.map(ProductMapper::toDto);
	}

	@Transactional(readOnly = true)
	@Override
	public ProductDto findById(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return ProductMapper.toDto(product);
	}

	@Transactional
	@Override
	public ProductDto createProduct(ProductCreateDto productDto) {
		List<Long> categoryIds = productDto.getCategoryIds();
		
		Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));

		if (categories.size() != categoryIds.size()) {
		    throw new ResourceNotFoundException("One or more category IDs are invalid");
		}
		
		Product product = ProductMapper.fromCreateDto(productDto, categories);

		product = repository.save(product);

		return ProductMapper.toDto(product);
	}

}
