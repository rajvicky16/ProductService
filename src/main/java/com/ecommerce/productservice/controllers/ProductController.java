package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable(name = "id") Long productId){
        return productService.getSingleProduct(productId);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return new Product();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long productId){
        return;
    }

    @PatchMapping("/{id}")
    public void updateProduct(@PathVariable(name = "id") Long productId, @RequestBody Product product){
        return;
    }

    @PutMapping("/{id}")
    public void replaceProduct(@PathVariable(name = "id") Long productId, @RequestBody Product product){
        return;
    }
}
