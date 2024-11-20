package com.ecommerce.productservice.configs;

import com.ecommerce.productservice.models.Product;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Patcher {
    public void doPatchUpdateForProduct(Product fromProduct, Product incompleteProduct) throws IllegalAccessException {
        Class<?> productClass = Product.class;
        Field[] fields = productClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object currFieldVal = field.get(incompleteProduct);
            if (currFieldVal != null) {
                field.set(fromProduct, currFieldVal);
            }

            field.setAccessible(false);
        }
    }
}
