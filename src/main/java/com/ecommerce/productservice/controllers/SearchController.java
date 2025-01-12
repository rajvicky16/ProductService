package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.Products.ResponseProductDto;
import com.ecommerce.productservice.dtos.Search.SearchResponseDto;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.Search.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<SearchResponseDto> searchAllProducts(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();

        Page<Product> productPage = searchService.searchAllProducts(pageNumber, pageSize);

        List<ResponseProductDto> responseProductDtos = new ArrayList<>();
        for(Product product : productPage.getContent()){
            responseProductDtos.add(ResponseProductDto.createFromProduct(product));
        }

        Pageable pageable = PageRequest.of(productPage.getNumber(), productPage.getSize());
        Page<ResponseProductDto> responseProductDtoPage = new PageImpl<>(responseProductDtos, pageable, productPage.getTotalElements());
        searchResponseDto.setProductsResponsePage(responseProductDtoPage);

        return new ResponseEntity<>(searchResponseDto, HttpStatus.OK);
    }
}
