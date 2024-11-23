package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.InvalidRequestException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(Product product) throws InvalidRequestException;

    void deleteProduct(Long productId) throws ProductNotFoundException;

    Product updateProduct(Long productId, Product product) throws IllegalAccessException, ProductNotFoundException;

    Product replaceProduct(Long productId, Product product) throws ProductNotFoundException;
}
