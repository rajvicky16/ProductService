package com.ecommerce.productservice.configs;

import com.ecommerce.productservice.models.Product;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Patcher {
/*  Patch update using reflection
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
        }*/
    public void doPatchUpdateForProduct(Product fromProduct, Product incompleteProduct){
        if(incompleteProduct.getTitle() != null){
            fromProduct.setTitle(incompleteProduct.getTitle());
        }
        if(incompleteProduct.getPrice() != null){
            fromProduct.setPrice(incompleteProduct.getPrice());
        }
        if(incompleteProduct.getCategory() != null && !incompleteProduct.getCategory().getValue().isEmpty()){
            fromProduct.setCategory(incompleteProduct.getCategory());
        }
        if(incompleteProduct.getDescription() != null){
            fromProduct.setDescription(incompleteProduct.getDescription());
        }
        if(incompleteProduct.getImage() != null){
            fromProduct.setImage(incompleteProduct.getImage());
        }
    }
}
