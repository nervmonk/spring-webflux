package com.dwikyryan.webflux_playground.sec06.assignment;

import java.util.function.BiFunction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CalculatorAssignment {
    @Bean
    public RouterFunction<ServerResponse> calculator(){
        return RouterFunctions.route()
        .path("calculator", this::calculatorRoutes)
        .build();
    }

    private RouterFunction<ServerResponse> calculatorRoutes(){
        return RouterFunctions.route()
        .GET("/{a}/0", badRequest("b cannot be 0"))
        .GET("/{a}/{b}", isOperation("+"), handle((a, b) -> a + b))
        .GET("/{a}/{b}", isOperation("-"), handle((a, b) -> a - b))
        .GET("/{a}/{b}", isOperation("/"), handle((a, b) -> a / b))
        .GET("/{a}/{b}", isOperation("*"), handle((a, b) -> a * b))
        .GET("/{a}/{b}", badRequest("operation header should be + - * /"))
        .build();
    }

    private RequestPredicate isOperation(String operation){
        return RequestPredicates.headers(h -> operation.equals(h.firstHeader("operation")));
    }

    private HandlerFunction<ServerResponse> handle(BiFunction<Integer, Integer, Integer> function){
        return req -> {
            var a = Integer.parseInt(req.pathVariable("a"));
            var b = Integer.parseInt(req.pathVariable("b"));
            var result = function.apply(a, b);
            return ServerResponse.ok().bodyValue(result);
        };
    }

    public HandlerFunction<ServerResponse> badRequest(String message){
        return req -> ServerResponse.badRequest().bodyValue(message);
    }
}
