package com.icaro.freitas.iecommerce_products.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.entity.Product;

public interface IProductService {
	
	Page<ProductDto> findAll(Specification<Product> spec, Pageable pageable);

}
