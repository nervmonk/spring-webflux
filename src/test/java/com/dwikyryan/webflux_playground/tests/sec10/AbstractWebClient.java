package com.dwikyryan.webflux_playground.tests.sec10;

import java.util.function.Consumer;

import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class AbstractWebClient {

    protected WebClient createWebClient() {
        return createWebClient(b -> {
        });
    }

    protected <T> Consumer<T> print() {
        return item -> log.info("received: {}", item);
    }

    protected WebClient createWebClient(Consumer<WebClient.Builder> consumer) {
        var builder = WebClient.builder()
                .baseUrl("http://localhost:7070/demo03");

        consumer.accept(builder);
        return builder.build();
    }
}
