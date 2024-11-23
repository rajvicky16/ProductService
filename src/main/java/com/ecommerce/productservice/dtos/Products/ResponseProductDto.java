package com.ecommerce.productservice.dtos.Products;

import com.ecommerce.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProductDto {
    private Long id;
    private String title;
    private String category;
    private String description;
    private String image;
    private Double price;

    public static ResponseProductDto createFromProduct(Product product){
        ResponseProductDto responseProductDto = new ResponseProductDto();
        responseProductDto.setId(product.getId());
        responseProductDto.setTitle(product.getTitle());
        responseProductDto.setCategory(product.getCategory() == null ? null : product.getCategory().getValue());
        responseProductDto.setDescription(product.getDescription());
        responseProductDto.setImage(product.getImage());
        responseProductDto.setPrice(product.getPrice());

        return responseProductDto;
    }
}
