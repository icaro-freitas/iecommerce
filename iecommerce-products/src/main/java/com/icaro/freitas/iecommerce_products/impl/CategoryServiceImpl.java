package com.icaro.freitas.iecommerce_products.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icaro.freitas.iecommerce_products.dto.CategoryDto;
import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.mapper.CategoryMapper;
import com.icaro.freitas.iecommerce_products.repository.CategoryRepository;
import com.icaro.freitas.iecommerce_products.service.ICategoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

	private CategoryRepository repository;

	@Transactional(readOnly = true)
	@Override
	public Page<CategoryDto> findAll(Pageable pageable) {
		Page<Category> categories = repository.findAll(pageable);
		return categories.map(x -> CategoryMapper.toDto(x));
	}

}
