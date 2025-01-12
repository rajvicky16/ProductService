package com.ecommerce.productservice.services.Search;

import com.ecommerce.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {
    Page<Product> searchAllProducts(int pageNumber, int pageSize);
}
