package com.ecommerce.productservice.services;

import com.ecommerce.productservice.configs.Patcher;
import com.ecommerce.productservice.dtos.FakeStore.FakeStoreRequestProductDto;
import com.ecommerce.productservice.dtos.FakeStore.FakeStoreResponseProductDto;
import com.ecommerce.productservice.exceptions.InvalidRequestException;
import com.ecommerce.productservice.exceptions.ProductNotFoundException;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service("fakeStoreProductServiceImpl")
//@Primary
public class FakeStoreProductServiceImpl implements ProductService{
    private RestTemplate restTemplate;
    private Patcher patcher;

    public FakeStoreProductServiceImpl(RestTemplate restTemplate, Patcher patcher) {
        this.restTemplate = restTemplate;
        this.patcher = patcher;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        FakeStoreResponseProductDto fakeStoreResponseProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreResponseProductDto.class);

        if(fakeStoreResponseProductDto == null){
            throw new ProductNotFoundException(productId, "Product Not Found. Please enter valid Product ID");
        }

        return fakeStoreResponseProductDto.convertToProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreResponseProductDto[] fakeStoreResponseProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreResponseProductDto[].class);

        List<Product> products = new ArrayList<>();
        for(FakeStoreResponseProductDto fakeStoreResponseProductDto : fakeStoreResponseProductDtos){
            products.add(fakeStoreResponseProductDto.convertToProduct());
        }

        return products;
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

        FakeStoreRequestProductDto fakeStoreRequestProductDto = FakeStoreRequestProductDto.createFakeStoreProductDtoFromObject(product);

        FakeStoreResponseProductDto fakeStoreResponseProductDto = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreRequestProductDto, FakeStoreResponseProductDto.class);

        return fakeStoreResponseProductDto.convertToProduct();
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        getSingleProduct(productId);

        restTemplate.exchange("https://fakestoreapi.com/products/" + productId, HttpMethod.DELETE, null, FakeStoreResponseProductDto.class);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws IllegalAccessException, ProductNotFoundException {
        Product existingFakeStoreProduct = getSingleProduct(productId);
        patcher.doPatchUpdateForProduct(existingFakeStoreProduct, product);

        FakeStoreRequestProductDto fakeStoreRequestProductDto = FakeStoreRequestProductDto.createFakeStoreProductDtoFromObject(existingFakeStoreProduct);

        FakeStoreResponseProductDto fakeStoreResponseProductDto = restTemplate.patchForObject("https://fakestoreapi.com/products/" + productId, fakeStoreRequestProductDto, FakeStoreResponseProductDto.class);

        return fakeStoreResponseProductDto.convertToProduct();
    }

    @Override
    public Product replaceProduct(Long productId, Product product) throws ProductNotFoundException {
        getSingleProduct(productId);

        FakeStoreRequestProductDto fakeStoreRequestProductDto = FakeStoreRequestProductDto.createFakeStoreProductDtoFromObject(product);

        HttpEntity<FakeStoreRequestProductDto> fakeStoreRequestProductDtoHttpEntity = new HttpEntity<>(fakeStoreRequestProductDto);

        ResponseEntity<FakeStoreResponseProductDto> fakeStoreResponseProductDtoResponseEntity = restTemplate.exchange("https://fakestoreapi.com/products/" + productId, HttpMethod.PUT, fakeStoreRequestProductDtoHttpEntity, FakeStoreResponseProductDto.class);

        return fakeStoreResponseProductDtoResponseEntity.getBody().convertToProduct();
    }
}
