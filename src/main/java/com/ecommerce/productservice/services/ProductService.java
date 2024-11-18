package com.ecommerce.productservice.services;

import com.ecommerce.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product getSingleProduct(Long productId);

    List<Product> getAllProducts();

    Product createProduct(Product product);

    void deleteProduct(Long productId);

    Product updateProduct(Long productId, Product product);

    Product replaceProduct(Long productId, Product product);
}
