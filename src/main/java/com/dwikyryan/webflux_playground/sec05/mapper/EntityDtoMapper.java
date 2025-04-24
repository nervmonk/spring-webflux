package com.dwikyryan.webflux_playground.sec05.mapper;

import com.dwikyryan.webflux_playground.sec05.dto.CustomerDto;
import com.dwikyryan.webflux_playground.sec05.entity.Customer;

public class EntityDtoMapper {

    private EntityDtoMapper() {
    }

    public static Customer toEntity(CustomerDto customerDto) {
        var customer = new Customer();
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setId(customerDto.id());

        return customer;
    }

    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail());
    }
}
