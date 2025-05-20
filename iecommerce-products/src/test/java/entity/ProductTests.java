package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.icaro.freitas.iecommerce_products.entity.Product;
import com.icaro.freitas.iecommerce_products.testutil.ProductFactory;

public class ProductTests {
	
	@Test
	void decreaseProductQuantityShouldThrowIllegalArgumentExceptionIfAmountSpecifiedIsHigherThanProductQuantity() {
		final int amountGreaterThanStock  = 51;
		Product product = ProductFactory.createProduct();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			product.decreaseProductQuantity(amountGreaterThanStock);
		});
		
	}

}
