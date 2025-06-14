package com.icaro.freitas.iecommerce_products.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;	
	@Column(precision = 10, scale = 2)
	private BigDecimal price;
	@Setter(AccessLevel.NONE)
	private Integer quantity;
	private String imageUrl;
	@Column(unique = true)
	private String slug;
	@Column(nullable = false)
	private Boolean active = true;

	@Setter(AccessLevel.NONE)
	@ManyToMany
	@JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();

	@Override
	public int hashCode() {
		return Objects.hash(id, slug);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id) && Objects.equals(slug, other.slug);
	}

	public void increaseProductQuantity(int amount) {
		this.quantity += amount;
	};

	public void decreaseProductQuantity(int amount) {
		if (amount > this.quantity) {
			throw new IllegalArgumentException("Cannot decrease quantity: not enough stock.");
		}
		this.quantity -= amount;
	};

}
