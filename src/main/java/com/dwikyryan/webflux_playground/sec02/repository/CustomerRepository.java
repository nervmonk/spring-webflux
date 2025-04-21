package com.dwikyryan.webflux_playground.sec02.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.dwikyryan.webflux_playground.sec02.entity.Customer;

import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
    Flux<Customer> findByName(String name);

    Flux<Customer> findByEmailEndingWith(String domain);
}
