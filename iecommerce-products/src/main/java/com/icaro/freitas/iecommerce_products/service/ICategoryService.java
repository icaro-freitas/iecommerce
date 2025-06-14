package com.icaro.freitas.iecommerce_products.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;

public interface ICategoryService {
	
	Page<CategoryDto> findAll(Pageable pageable);

}
