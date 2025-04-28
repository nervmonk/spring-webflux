package com.dwikyryan.webflux_playground.sec09.service;

import org.springframework.stereotype.Service;

import com.dwikyryan.webflux_playground.sec09.dto.ProductDto;
import com.dwikyryan.webflux_playground.sec09.mapper.EntityDtoMapper;
import com.dwikyryan.webflux_playground.sec09.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final Sinks.Many<ProductDto> sink;

    public ProductService(ProductRepository repository, Sinks.Many<ProductDto> sink) {
        this.repository = repository;
        this.sink = sink;
    }

    public Mono<ProductDto> saveProducts(Mono<ProductDto> mono) {
        return mono.map(EntityDtoMapper::toEntity)
                .flatMap(this.repository::save)
                .map(EntityDtoMapper::toDto)
                .doOnNext(this.sink::tryEmitNext);
    }

    public Flux<ProductDto> productStream(){
        return this.sink.asFlux();
    }


}
