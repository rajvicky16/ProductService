package com.ecommerce.productservice.dtos.FakeStore;

import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreResponseProductDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;

    public Product convertToProduct() {
        Product product = new Product();
        product.setCategory(new Category());

        product.setId(this.getId());
        product.setTitle(this.getTitle());
        product.setPrice(this.getPrice());
        product.getCategory().setValue(this.getCategory());
        product.setDescription(this.getDescription());
        product.setImage(this.getImage());

        return product;
    }
}
