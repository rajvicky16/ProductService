package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    void patchProduct(Product source, @MappingTarget Product target);
}

