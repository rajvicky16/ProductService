package com.ecommerce.productservice.services.Search;

import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService{
    private ProductRepository productRepository;

    public SearchServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> searchAllProducts(int pageNumber, int pageSize) {
        Page<Product> productRepositoryAll = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return productRepositoryAll;
    }
}
