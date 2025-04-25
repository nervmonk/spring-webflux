package com.dwikyryan.webflux_playground.sec06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.dwikyryan.webflux_playground.sec06.exceptions.CustomerNotFoundException;
import com.dwikyryan.webflux_playground.sec06.exceptions.InvalidInputException;

@Configuration
public class RouterConfiguration {

    private final CustomerRequestHandler handler;

    private final ApplicationExceptionHandler applicationExceptionHandler;

    public RouterConfiguration(CustomerRequestHandler handler,
            ApplicationExceptionHandler applicationExceptionHandler) {
        this.handler = handler;
        this.applicationExceptionHandler = applicationExceptionHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> customerRoutes() {
        // order of the routes matter
        return RouterFunctions.route()
                .GET("/customers", this.handler::allCustomers)
                .GET("/customers/paginated", this.handler::paginatedCustomers)
                .GET("/customers/{id}", this.handler::getCustomer)
                .POST("/customers", this.handler::saveCustomer)
                .PUT("/customers/{id}", this.handler::updateCustomer)
                .DELETE("/customers/{id}", this.handler::deleteCustomer)
                .onError(CustomerNotFoundException.class, this.applicationExceptionHandler::handleException)
                .onError(InvalidInputException.class, this.applicationExceptionHandler::handleException)
                .build();
    }
}
