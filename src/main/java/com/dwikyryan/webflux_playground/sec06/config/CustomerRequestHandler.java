package com.dwikyryan.webflux_playground.sec06.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.dwikyryan.webflux_playground.sec06.exceptions.ApplicationException;
import com.dwikyryan.webflux_playground.sec06.dto.CustomerDto;
import com.dwikyryan.webflux_playground.sec06.service.CustomerService;
import com.dwikyryan.webflux_playground.sec06.validator.RequestValidator;

import reactor.core.publisher.Mono;

@Service
public class CustomerRequestHandler {

    private final CustomerService service;

    public CustomerRequestHandler(CustomerService service) {
        this.service = service;
    }

    public Mono<ServerResponse> allCustomers(ServerRequest request) {
        // request.pathVariable()
        // request.headers()
        // request.queryParam()
        return this.service.getAllCustomers()
                .as(flux -> ServerResponse.ok().body(flux, CustomerDto.class));

    }

    public Mono<ServerResponse> paginatedCustomers(ServerRequest request) {
        var page = request.queryParam("page").map(Integer::parseInt).orElse(1);
        var size = request.queryParam("size").map(Integer::parseInt).orElse(3);
        return this.service.getAllCustomers(page, size)
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);

    }

    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return this.service.getCustomerById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);

    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(this.service::saveCustomer)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(validatedReq -> this.service.updateCustomer(id, validatedReq))
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return this.service.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then(ServerResponse.ok().build());

    }
}
