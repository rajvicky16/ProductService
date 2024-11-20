package com.ecommerce.productservice.dtos.FakeStore;

import com.ecommerce.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreRequestProductDto {
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;

    public static FakeStoreRequestProductDto createFakeStoreProductDtoFromObject(Product product) {
        FakeStoreRequestProductDto fakeStoreRequestProductDto = new FakeStoreRequestProductDto();

        fakeStoreRequestProductDto.setTitle(product.getTitle());
        fakeStoreRequestProductDto.setPrice(product.getPrice());
        fakeStoreRequestProductDto.setCategory(product.getCategory() == null ? null : product.getCategory().getValue());
        fakeStoreRequestProductDto.setDescription(product.getDescription());
        fakeStoreRequestProductDto.setImage(product.getImage());

        return fakeStoreRequestProductDto;
    }
}
