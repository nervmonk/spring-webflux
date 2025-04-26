package com.dwikyryan.webflux_playground.tests.sec07;

import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.dwikyryan.webflux_playground.tests.sec07.dto.CalculatorResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
class Lec05ErrorResponseTest extends AbstractWebClient {
    private final WebClient client = createWebClient();

    @Test
    void handlingError() {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "-")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                // .onErrorReturn(new CalculatorResponse(0, 0, null, 0.0))
                .doOnError(WebClientResponseException.class,
                        ex -> log.info("{}", ex.getResponseBodyAs(ProblemDetail.class)))
                .onErrorReturn(WebClientResponseException.InternalServerError.class,
                        new CalculatorResponse(0, 0, null, 0.0))
                .onErrorReturn(WebClientResponseException.BadRequest.class, new CalculatorResponse(0, 0, null, -1.0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void exchange() {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "+")
                .exchangeToMono(cr -> decode(cr))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    private static Mono<CalculatorResponse> decode(ClientResponse clientResponse) {
        // clientResponse.cookies();
        // clientResponse.headers();
        log.info("status code: {}", clientResponse.statusCode());

        if (clientResponse.statusCode().isError()) {
            return clientResponse.bodyToMono(ProblemDetail.class)
                    .doOnNext(pd -> log.info("{}", pd))
                    .then(Mono.empty());
        }

        return clientResponse.bodyToMono(CalculatorResponse.class);
    }
}
