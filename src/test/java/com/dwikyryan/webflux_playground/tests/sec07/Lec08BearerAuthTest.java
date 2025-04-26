package com.dwikyryan.webflux_playground.tests.sec07;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.dwikyryan.webflux_playground.tests.sec07.dto.Product;

import reactor.test.StepVerifier;

class Lec08BearerAuthTest extends AbstractWebClient {
    private final WebClient client = createWebClient(
            b -> b.defaultHeaders(h -> h.setBearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")));

    @Test
    void bearerAuth() {
        this.client.get()
                .uri("/lec08/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
