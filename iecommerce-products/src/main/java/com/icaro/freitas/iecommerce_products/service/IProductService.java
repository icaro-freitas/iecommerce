package com.icaro.freitas.iecommerce_products.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.dto.ProductFilterDto;

public interface IProductService {
	
	Page<ProductDto> findAll(ProductFilterDto filter,Pageable pageable);
	
	ProductDto findById(Long id);
	
	ProductDto createProduct(ProductDto productDto);

}
