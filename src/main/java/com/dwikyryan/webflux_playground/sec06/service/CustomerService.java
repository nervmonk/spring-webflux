package com.dwikyryan.webflux_playground.sec06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dwikyryan.webflux_playground.sec06.dto.CustomerDto;
import com.dwikyryan.webflux_playground.sec06.mapper.EntityDtoMapper;
import com.dwikyryan.webflux_playground.sec06.repository.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<CustomerDto> getAllCustomers() {
        return this.customerRepository.findAll()
                .map(EntityDtoMapper::toDto);
    }

    public Flux<CustomerDto> getAllCustomers(Integer page, Integer size) {
        return this.customerRepository.findBy(PageRequest.of(page - 1, size))
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> getCustomerById(Integer id) {
        return this.customerRepository.findById(id)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> mono) {
        return mono.map(EntityDtoMapper::toEntity)
                .flatMap(this.customerRepository::save)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Integer id, Mono<CustomerDto> mono) {
        return this.customerRepository.findById(id)
                .flatMap(entity -> mono)
                .map(EntityDtoMapper::toEntity)
                .doOnNext(c -> c.setId(id))
                .flatMap(this.customerRepository::save)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<Boolean> deleteCustomerById(Integer id) {
        return this.customerRepository.deleteCustomerById(id);
    }
}
