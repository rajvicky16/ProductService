package com.ecommerce.productservice.models;

import com.ecommerce.productservice.exceptions.InvalidRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
public class Product extends BaseModel{
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Double price;
    @ManyToOne
    private Category category;
    @Column(length = 1000)
    private String description;
    private String image;

    public void validateMandatoryFields() throws InvalidRequestException {
        if(title == null || title.isEmpty()){
            throw new InvalidRequestException("Product title cannot be empty.", "Please pass valid product title in request body");
        }
        if(price == null || price <= 0D){
            throw new InvalidRequestException("Invalid Product price.", "Please pass valid price in request body");
        }
    }
}
