package com.dwikyryan.webflux_playground.tests.sec07;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.dwikyryan.webflux_playground.tests.sec07.dto.Product;

public class Lec01MonoTest extends AbstractWebClient {
    private final WebClient client = createWebClient();

    @Test
    public void simpleGet() throws InterruptedException{
        this.client.get()
                .uri("/lec01/product/1")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .subscribe();

        Thread.sleep(Duration.ofSeconds(2));
    }

    @Test
    public void concurrentRequests() throws InterruptedException{

        for (int i = 0; i <= 5; i++) {
            this.client.get()
            .uri("/lec01/product/" + i)
            .retrieve()
            .bodyToMono(Product.class)
            .doOnNext(print())
            .subscribe();   
        }

        Thread.sleep(Duration.ofSeconds(2));
    }
}
