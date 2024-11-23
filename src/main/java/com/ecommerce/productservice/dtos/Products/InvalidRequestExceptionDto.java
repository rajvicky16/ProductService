package com.ecommerce.productservice.dtos.Products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidRequestExceptionDto {
    private String message;
    private String[] suggestionToFix;
}
