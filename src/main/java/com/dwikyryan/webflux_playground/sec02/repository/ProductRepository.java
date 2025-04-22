package com.dwikyryan.webflux_playground.sec02.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.dwikyryan.webflux_playground.sec02.entity.Product;

import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer>{

    Flux<Product> findByPriceBetween(Integer from, Integer to);

    Flux<Product> findBy(Pageable pageable);
    
}
