package com.dwikyryan.webflux_playground.sec05.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwikyryan.webflux_playground.sec05.dto.CustomerDto;
import com.dwikyryan.webflux_playground.sec05.exceptions.ApplicationException;
import com.dwikyryan.webflux_playground.sec05.filter.Category;
import com.dwikyryan.webflux_playground.sec05.service.CustomerService;
import com.dwikyryan.webflux_playground.sec05.validator.RequestValidator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Flux<CustomerDto> allCustomers(@RequestAttribute("category") Category category) {
        System.out.println(category);
        return this.customerService.getAllCustomers();
    }

    @GetMapping("paginated")
    public Mono<List<CustomerDto>> allCustomers(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {
        return this.customerService.getAllCustomers(page, size)
                .collectList();
    }

    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomer(@PathVariable Integer id) {
        return this.customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> mono) {
        return mono.transform(RequestValidator.validate())
                .as(this.customerService::saveCustomer);
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable Integer id,
            @RequestBody Mono<CustomerDto> mono) {
        return mono.transform(RequestValidator.validate())
                .as(validReq -> this.customerService.updateCustomer(id, validReq))
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return this.customerService.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then();
    }

}
