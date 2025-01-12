package com.ecommerce.productservice.dtos.Search;

import com.ecommerce.productservice.dtos.Products.ResponseProductDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class SearchResponseDto {
    private Page<ResponseProductDto> productsResponsePage;
}
