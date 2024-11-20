package com.ecommerce.productservice.dtos.Products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundExceptionDto {
    private Long id;
    private String message;
}
