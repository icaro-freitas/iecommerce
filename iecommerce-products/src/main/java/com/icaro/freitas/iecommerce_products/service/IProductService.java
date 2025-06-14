package com.icaro.freitas.iecommerce_products.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;

public interface IProductService {
	
	Page<ProductDto> findAll(Pageable pageable);

}
