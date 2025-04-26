package com.dwikyryan.webflux_playground.tests.sec07;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.dwikyryan.webflux_playground.tests.sec07.dto.Product;

import reactor.test.StepVerifier;

class Lec02FluxTest extends AbstractWebClient {
    private final WebClient client = createWebClient();

    @Test
    void streamingResponse() {
        this.client.get()
                .uri("/lec02/product/stream")
                .retrieve()
                .bodyToFlux(Product.class)
                .take(Duration.ofSeconds(3))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
