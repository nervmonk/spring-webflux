package com.dwikyryan.webflux_playground.sec04.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.dwikyryan.webflux_playground.sec04.entity.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    @Modifying
    @Query("delete from customer where id = :id")
    Mono<Boolean> deleteCustomerById(Integer id);

    Flux<Customer> findBy(Pageable pageable);
}
