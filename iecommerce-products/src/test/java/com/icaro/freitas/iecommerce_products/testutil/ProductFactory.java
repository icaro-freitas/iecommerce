package com.icaro.freitas.iecommerce_products.testutil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.icaro.freitas.iecommerce_products.entity.Category;
import com.icaro.freitas.iecommerce_products.entity.Product;

public class ProductFactory {

	public static Product createProduct() {

		Set<Category> categories = Set.of(new Category(1L, "Eletrônicos", null));

		Product product = new Product(1L, "Notebook asus vivobook", "É leve. É compacto. É o ASUS",
				new BigDecimal("2867.00"), 50, "img/notebook-asus-vivobook", "notebook-asus-vivobook", true,
				categories);

		return product;
	}
	
	public static List<Product> createProductList() {
		
		Set<Category> categories1 = Set.of(new Category(1L, "Eletrônicos", null));
		
		Set<Category> categories2 = Set.of(new Category(1L, "Livros", null));
		
		Set<Category> categories3 = Set.of(new Category(1L, "Esportes", null));
		
		Product product1 = new Product(1L, "Notebook asus vivobook", "É leve. É compacto. É o ASUS",
				new BigDecimal("2867.00"), 50, "img/notebook-asus-vivobook", "notebook-asus-vivobook", true,
				categories1);
		
		Product product2 = new Product(2L, "As Crônicas de Nárnia", "As Crônicas de Nárnia – Coleção de Luxo: O Leão, a Feiticeira e o Guarda-Roupa",
				new BigDecimal("41.76"), 100, "img/as-cronicas-de-narnia", "as-cronicas-de-narnia", true,
				categories2);
		
		Product product3 = new Product(3L, "Halter 4kg", "É a escolha ideal para quem busca qualidade e segurança",
				new BigDecimal("56.20"), 200, "img/halter-4k", "halter-4k", true,
				categories3);
		
		return List.of(product1, product2, product3);		
	}
	
}
