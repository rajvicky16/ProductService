package com.ecommerce.productservice.services;

import com.ecommerce.productservice.configs.Patcher;
import com.ecommerce.productservice.exceptions.InvalidRequestException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("productServiceDBImpl")
public class ProductServiceDBImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private Patcher patcher;

    public ProductServiceDBImpl(ProductRepository productRepository, Patcher patcher, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.patcher = patcher;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isEmpty() || optionalProduct.get().isDeleted()){
            throw new ProductNotFoundException(productId, "Product Not Found. Please enter valid Product ID");
        }

        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) throws InvalidRequestException {
        if(product.getId() != null){
            throw new InvalidRequestException("Invalid POST Request", "1. Try to remove productId from Request Body." + System.lineSeparator() + "2. Try to change Request type to PUT or PATCH if you wish to REPLACE or UPDATE existing product.");
        }

        product.validateMandatoryFields();
        if(product.getCategory() == null || product.getCategory().getValue().isEmpty()){
            throw new InvalidRequestException("Category field cannot be empty.", "Please pass valid category name in request body.");
        }

        Optional<Category> optionalCategory = categoryRepository.findByValueIgnoreCase(product.getCategory().getValue());
        Category category;
        if(optionalCategory.isEmpty()){
            product.getCategory().setCreatedAt(new Date());
            category = categoryRepository.save(product.getCategory());
        }
        else{
            category = optionalCategory.get();
        }
        product.setCategory(category);
        product.setCreatedAt(new Date());

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException{

    }

    @Override
    public Product updateProduct(Long productId, Product product) throws IllegalAccessException, ProductNotFoundException {
        return null;
    }

    @Override
    public Product replaceProduct(Long productId, Product product) throws ProductNotFoundException {
        return null;
    }
}
