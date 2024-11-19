package com.ecommerce.productservice.dtos.Products;

import com.ecommerce.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetAllProductsResponseDto {
    List<ResponseProductDto> responseProductDtoList;

    public static GetAllProductsResponseDto createGetAllProductsResponseDto(List<Product> productList){
        GetAllProductsResponseDto getAllProductsResponseDto = new GetAllProductsResponseDto();

        List<ResponseProductDto> responseProductDtos = new ArrayList<>();
        for(Product currProduct : productList){
            responseProductDtos.add(ResponseProductDto.createFromProduct(currProduct));
        }
        getAllProductsResponseDto.setResponseProductDtoList(responseProductDtos);

        return getAllProductsResponseDto;
    }
}
