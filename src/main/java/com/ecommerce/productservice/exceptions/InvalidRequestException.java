package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class InvalidRequestException extends Exception{
    String suggestionToFix;

    public InvalidRequestException(String message, String suggestionToFix){
        super(message);
        this.suggestionToFix = suggestionToFix;
    }
}
