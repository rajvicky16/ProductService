package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.Products.CreateProductDto;
import com.ecommerce.productservice.dtos.Products.GetAllProductsResponseDto;
import com.ecommerce.productservice.dtos.Products.ResponseProductDto;
import com.ecommerce.productservice.exceptions.InvalidRequestException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/products", "/products/"})
public class ProductController {
    private ProductService productService;

    public ProductController(@Qualifier("productServiceDBImpl") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> getSingleProduct(@PathVariable(name = "id") Long productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);

        ResponseEntity<ResponseProductDto> responseEntity = new ResponseEntity<>(
                ResponseProductDto.createFromProduct(product),
                HttpStatus.OK
        );

        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<GetAllProductsResponseDto> getAllProducts(){
        List<Product> productList = productService.getAllProducts();

        ResponseEntity<GetAllProductsResponseDto> responseEntity = new ResponseEntity<>(
                GetAllProductsResponseDto.createGetAllProductsResponseDto(productList),
                HttpStatus.OK
        );

        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<ResponseProductDto> createProduct(@RequestBody CreateProductDto createProductDto) throws InvalidRequestException {
        Product product = productService.createProduct(createProductDto.convertToProductObject());

        ResponseEntity<ResponseProductDto> responseEntity = new ResponseEntity<>(
                ResponseProductDto.createFromProduct(product),
                HttpStatus.OK
        );

        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(
                "Product deleted successfully",
                HttpStatus.OK
        );

        return responseEntity;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseProductDto> updateProduct(@PathVariable(name = "id") Long productId, @RequestBody CreateProductDto createProductDto) throws IllegalAccessException, ProductNotFoundException, InvalidRequestException {
        Product product = productService.updateProduct(productId, createProductDto.convertToProductObject());

        ResponseEntity<ResponseProductDto> responseEntity = new ResponseEntity<>(
                ResponseProductDto.createFromProduct(product),
                HttpStatus.OK
        );

        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDto> replaceProduct(@PathVariable(name = "id") Long productId, @RequestBody CreateProductDto createProductDto) throws ProductNotFoundException, InvalidRequestException {
        Product product = productService.replaceProduct(productId, createProductDto.convertToProductObject());

        ResponseEntity<ResponseProductDto> responseEntity = new ResponseEntity<>(
                ResponseProductDto.createFromProduct(product),
                HttpStatus.OK
        );

        return responseEntity;
    }
}
