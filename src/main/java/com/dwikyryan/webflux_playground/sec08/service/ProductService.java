package com.dwikyryan.webflux_playground.sec08.service;

import org.springframework.stereotype.Service;

import com.dwikyryan.webflux_playground.sec08.dto.ProductDto;
import com.dwikyryan.webflux_playground.sec08.mapper.EntityDtoMapper;
import com.dwikyryan.webflux_playground.sec08.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Flux<ProductDto> saveProducts(Flux<ProductDto> flux) {
        return flux.map(EntityDtoMapper::toEntity)
                .as(this.repository::saveAll)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<Long> getProductCount() {
        return this.repository.count();
    }

    public Flux<ProductDto> allProducts() {
        return this.repository.findAll()
                .map(EntityDtoMapper::toDto);
    }
}
