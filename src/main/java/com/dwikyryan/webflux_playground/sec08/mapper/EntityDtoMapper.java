package com.dwikyryan.webflux_playground.sec08.mapper;

import com.dwikyryan.webflux_playground.sec08.dto.ProductDto;
import com.dwikyryan.webflux_playground.sec08.entity.Product;

public class EntityDtoMapper {

    private EntityDtoMapper() {
    }

    public static Product toEntity(ProductDto productDto) {
        var product = new Product();
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setId(productDto.id());

        return product;
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getDescription(), product.getPrice());
    }
}
