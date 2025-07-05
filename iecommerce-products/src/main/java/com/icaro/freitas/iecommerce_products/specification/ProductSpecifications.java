package com.icaro.freitas.iecommerce_products.specification;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.entity.Product;

import jakarta.persistence.criteria.Join;

public class ProductSpecifications {

	public static Specification<Product> nameContains(String name) {
		return (root, query, criteriaBuilder) -> name == null ? null
				: criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
	}

	public static Specification<Product> descriptionContains(String description) {
		return (root, query, criteriaBuilder) -> description == null ? null
				: criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
						"%" + description.toLowerCase() + "%");
	}

	public static Specification<Product> categoryIdIn(List<Long> categoryIds) {
		return (root, query, cb) -> {
			if (categoryIds == null || categoryIds.isEmpty())
				return null;
			Join<Product, Category> join = root.join("categories");
			return join.get("id").in(categoryIds);
		};
	}

	public static Specification<Product> minPrice(BigDecimal minPrice) {
		return (root, query, cb) -> minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
	}

	public static Specification<Product> maxPrice(BigDecimal maxPrice) {
		return (root, query, cb) -> maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
	}

	public static Specification<Product> minQuantity(Integer minQuantity) {
		return (root, query, cb) -> minQuantity == null ? null
				: cb.greaterThanOrEqualTo(root.get("quantity"), minQuantity);
	}

	public static Specification<Product> maxQuantity(Integer maxQuantity) {
		return (root, query, cb) -> maxQuantity == null ? null
				: cb.lessThanOrEqualTo(root.get("quantity"), maxQuantity);
	}

	public static Specification<Product> isActive(Boolean active) {
		return (root, query, cb) -> active == null ? null : cb.equal(root.get("active"), active);
	}

}
