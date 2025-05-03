package com.icaro.freitas.iecommerce_products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.icaro.freitas.iecommerce_products.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	Page<Product> findAll(Pageable pageable);

}
