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
        return productRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Product createProduct(Product product) throws InvalidRequestException {
/*        if(product.getId() != null){
            throw new InvalidRequestException("Invalid POST Request", "1. Try to remove productId from Request Body." + System.lineSeparator() + "2. Try to change Request type to PUT or PATCH if you wish to REPLACE or UPDATE existing product.");
        }*/
        product.setId(null);
        product.validateMandatoryFields();
        if(product.getCategory() == null || product.getCategory().getValue().isEmpty()){
            throw new InvalidRequestException("Category field cannot be empty.", "Please pass valid category name in request body.");
        }

        Category category = getSavedCategory(product.getCategory());
        product.setCategory(category);
        product.setCreatedAt(new Date());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException{
        Product product = getSingleProduct(productId);
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws IllegalAccessException, ProductNotFoundException, InvalidRequestException {
        Product savedProduct = getSingleProduct(productId);
/*        if(product.getTitle() != null && product.getTitle().isEmpty()){
            throw new InvalidRequestException("Title field cannot be empty.", "Please pass valid Title name in request body. If you don't wish to update current product title, then please set Title field to null.");
        }
        if(product.getPrice() != null && product.getPrice() <= 0D){
            throw new InvalidRequestException("Not a valid amount.", "Please pass valid Price field in request body. If you don't wish to update current price, then please set price field to null.");
        }*/
        if(product.getTitle() != null && product.getTitle().isEmpty()){
            product.setTitle(null);
        }
        if(product.getPrice() != null && product.getPrice() <= 0D){
            product.setPrice(null);
        }
        if(product.getCategory() != null){
            if(!product.getCategory().getValue().isEmpty()){
                Category category = getSavedCategory(product.getCategory());
                product.setCategory(category);
            }
            else{
                product.setCategory(null);
                /*throw new InvalidRequestException("Category field cannot be empty.", "Please pass valid category name in request body. If you don't wish to update current category name, then please set category field to null.");*/
            }
        }

        patcher.doPatchUpdateForProduct(savedProduct, product);
        savedProduct.setId(productId);
        savedProduct.setUpdatedAt(new Date());

        return productRepository.save(savedProduct);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) throws ProductNotFoundException, InvalidRequestException {
        Product savedProduct = getSingleProduct(productId);

        product.validateMandatoryFields();
        if(product.getCategory() == null || product.getCategory().getValue().isEmpty()){
            throw new InvalidRequestException("Category field cannot be empty.", "Please pass valid category name in request body.");
        }

        Category category = getSavedCategory(product.getCategory());
        product.setId(productId);
        product.setCategory(category);
        product.setCreatedAt(savedProduct.getCreatedAt());
        product.setUpdatedAt(new Date());

        return productRepository.save(product);
    }

    private Category getSavedCategory(Category category) {
        Optional<Category> optionalCategory = categoryRepository.findByValueIgnoreCase(category.getValue());
        Category resCategory;

        if(optionalCategory.isEmpty()){
            category.setCreatedAt(new Date());
            resCategory = categoryRepository.save(category);
        }
        else{
            resCategory = optionalCategory.get();
        }

        return resCategory;
    }
}
