package com.ecommerce.productservice.dtos.Products;

import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;

    public static CreateProductDto createProductDtoFromObject(Product product) {
        CreateProductDto createProductDto = new CreateProductDto();

        createProductDto.setId(product.getId());
        createProductDto.setTitle(product.getTitle());
        createProductDto.setPrice(product.getPrice());
        createProductDto.setCategory(product.getCategory().getValue());
        createProductDto.setDescription(product.getDescription());
        createProductDto.setImage(product.getImage());

        return createProductDto;
    }

    public Product convertToProductObject() {
        Product product = new Product();

        product.setId(this.id == null ? null : this.id);
        product.setTitle(this.title == null ? null : this.title);
        product.setPrice(this.price == null ? null : this.price);
        if(this.category != null) {
            product.setCategory(new Category());
            product.getCategory().setValue(this.category);
        }
        product.setDescription(this.description == null ? null : this.description);
        product.setImage(this.image == null ? null : this.image);

        return product;
    }
}
