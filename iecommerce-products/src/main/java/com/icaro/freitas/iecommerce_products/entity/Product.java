package com.icaro.freitas.iecommerce_products.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Product extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(precision = 10, scale = 2)
	private BigDecimal price;
	private Integer quantity;
	private String imageUrl;
	@Column(unique = true)
	private String slug;
    @Column(nullable = false)
    private Boolean active = true;

}
