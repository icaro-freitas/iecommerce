package com.icaro.freitas.iecommerce_products.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icaro.freitas.iecommerce_products.dto.ProductDto;
import com.icaro.freitas.iecommerce_products.entity.Product;
import com.icaro.freitas.iecommerce_products.mapper.ProductMapper;
import com.icaro.freitas.iecommerce_products.repository.ProductRepository;
import com.icaro.freitas.iecommerce_products.service.IProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

	private ProductRepository repository;

	@Transactional(readOnly = true)
	@Override
	public Page<ProductDto> findAll(Specification<Product> spec,Pageable pageable) {
		Page<Product> productPage = repository.findAll(pageable);

		List<ProductDto> dtoList = productPage.getContent().stream().map(p -> ProductMapper.toDto(p)).toList();

		return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
	}

}
