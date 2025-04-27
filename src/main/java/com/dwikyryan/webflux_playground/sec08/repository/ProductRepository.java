package com.dwikyryan.webflux_playground.sec08.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.dwikyryan.webflux_playground.sec08.entity.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

}
