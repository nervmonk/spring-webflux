package com.dwikyryan.webflux_playground.tests.sec07;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.dwikyryan.webflux_playground.tests.sec07.dto.Product;

import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

@Slf4j
class Lec09ExchangeFilterTest extends AbstractWebClient {
    private final WebClient client = createWebClient(
            b -> b.filter(tokenGenerator()).filter(requestLogger()));

    @Test
    void exchangeFilter() {
        for (int i = 1; i <= 5; i++) {
            this.client.get()
                    .uri("/lec09/product/{id}", i)
                    .attribute("enable-logging", i % 2 == 0)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .doOnNext(print())
                    .then()
                    .as(StepVerifier::create)
                    .expectComplete()
                    .verify();
        }
    }

    private static ExchangeFilterFunction tokenGenerator() {
        return (request, next) -> {
            var token = UUID.randomUUID().toString().replace("-", "");
            log.info("generated token: {}", token);
            var modifiedRequest = ClientRequest.from(request).headers(h -> h.setBearerAuth(token)).build();
            return next.exchange(modifiedRequest);
        };
    }

    private static ExchangeFilterFunction requestLogger() {
        return (request, next) -> {
            var isEnabled = (Boolean) request.attributes().getOrDefault("enable-logging", false);
            if (isEnabled) {
                log.info("request url - {}: {}", request.method(), request.url());
            }
            return next.exchange(request);
        };
    }
}
