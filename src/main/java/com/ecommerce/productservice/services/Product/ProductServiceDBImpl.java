package com.ecommerce.productservice.services.Product;

import com.ecommerce.productservice.exceptions.InvalidRequestException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.mapper.ProductMapper;
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
    private ProductMapper productMapper;

    public ProductServiceDBImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isEmpty() || optionalProduct.get().getIsDeleted()){
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
        product.setId(null);
        product.validateMandatoryFields();
        if(product.getCategory() == null || product.getCategory().getValue().isEmpty()){
            throw new InvalidRequestException("Category field cannot be empty.", "Please pass valid category name in request body.");
        }

        Category category = getSavedCategory(product.getCategory());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException{
        Product product = getSingleProduct(productId);
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException, InvalidRequestException {
        Product savedProduct = getSingleProduct(productId);

        if(product.getTitle() != null && product.getTitle().isEmpty()){
            product.setTitle(null);
        }
        if(product.getCategory() != null){
            if(!product.getCategory().getValue().isEmpty()){
                Category category = getSavedCategory(product.getCategory());
                product.setCategory(category);
            }
            else{
                product.setCategory(null);
            }
        }

        productMapper.patchProduct(product, savedProduct);
        savedProduct.setId(productId);
        savedProduct.validateMandatoryFields();

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
        product.setCategory(category);
        product.setId(productId);

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
