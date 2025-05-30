package com.icaro.freitas.iecommerce_products.testutil;

import java.util.ArrayList;
import java.util.List;

import com.icaro.freitas.iecommerce_products.entity.Category;

public class CategoryFactory {

	public static Category createCategory() {

		Category category = new Category(1L, "Eletrônicos");

		return category;
	}

	public static List<Category> createCategoryList() {

		Category category1 = new Category(1L, "Eletrônicos");
		Category category2 = new Category(2L, "Livros");
		Category category3 = new Category(3L, "Esportes");
		Category category4 = new Category(4L, "Eletrodomésticos");

		return new ArrayList<>(List.of(category1, category2, category3, category4));
	}

}
