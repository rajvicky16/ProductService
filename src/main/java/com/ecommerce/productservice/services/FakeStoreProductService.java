package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.FakeStore.FakeStoreRequestProductDto;
import com.ecommerce.productservice.dtos.FakeStore.FakeStoreResponseProductDto;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        FakeStoreResponseProductDto fakeStoreResponseProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreResponseProductDto.class);

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
    public Product createProduct(Product product) {
        FakeStoreRequestProductDto fakeStoreRequestProductDto = FakeStoreRequestProductDto.createFakeStoreProductDtoFromObject(product);

        FakeStoreResponseProductDto fakeStoreResponseProductDto = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreRequestProductDto, FakeStoreResponseProductDto.class);

        return fakeStoreResponseProductDto.convertToProduct();
    }

    @Override
    public void deleteProduct(Long productId) {
        ResponseEntity<FakeStoreResponseProductDto> fakeStoreResponseProductDtoResponseEntity = restTemplate.exchange("https://fakestoreapi.com/products/" + productId, HttpMethod.DELETE, null, FakeStoreResponseProductDto.class);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
/*        FakeStoreRequestProductDto fakeStoreRequestProductDto = FakeStoreRequestProductDto.createFakeStoreProductDtoFromObject(product);

        FakeStoreResponseProductDto fakeStoreResponseProductDto = restTemplate.patchForObject("https://fakestoreapi.com/products/" + productId, fakeStoreRequestProductDto, FakeStoreResponseProductDto.class);

        return fakeStoreResponseProductDto.convertToProduct();*/
        return new Product();
    }

    @Override
    public void replaceProduct(Long productId, Product product) {

    }
}
