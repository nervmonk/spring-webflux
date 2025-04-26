package com.dwikyryan.webflux_playground.tests.sec07;

import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.dwikyryan.webflux_playground.tests.sec07.dto.Product;

import reactor.test.StepVerifier;

class Lec04HeaderTest extends AbstractWebClient {
    private final WebClient client = createWebClient(b -> b.defaultHeader("caller-id", "ryan-dwiky"));

    @Test
    void defaultHeader() {
        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void overrideHeader() {
        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .header("caller-id", "mr-lebowski")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void headersWithMap() {
        var map = Map.of(
                "caller-id", "john-krazowski",
                "some-key", "some-value");

        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .headers(h -> h.setAll(map))
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
