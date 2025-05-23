package com.dwikyryan.webflux_playground.sec05.validator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.dwikyryan.webflux_playground.sec05.dto.CustomerDto;
import com.dwikyryan.webflux_playground.sec05.exceptions.ApplicationException;

import reactor.core.publisher.Mono;

public class RequestValidator {

    private RequestValidator(){}

    public static UnaryOperator<Mono<CustomerDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationException.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationException.missingValidEmail());
    }

    private static Predicate<CustomerDto> hasName() {
        return dto -> Objects.nonNull(dto.name());
    }

    private static Predicate<CustomerDto> hasValidEmail() {
        return dto -> Objects.nonNull(dto.email()) && dto.email().contains("@");
    }
}
