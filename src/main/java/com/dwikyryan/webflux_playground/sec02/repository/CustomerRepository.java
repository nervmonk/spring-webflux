package com.dwikyryan.webflux_playground.sec02.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.dwikyryan.webflux_playground.sec02.entity.Customer;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
    
}
