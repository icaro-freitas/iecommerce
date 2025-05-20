package com.icaro.freitas.iecommerce_products.testutil;

import com.icaro.freitas.iecommerce_products.entity.Category;

public class CategoryFactory {
	
	public static Category createCategory() {

		Category category = new Category(1L, "Eletrônicos", null);
		
		return category;
	}

}
