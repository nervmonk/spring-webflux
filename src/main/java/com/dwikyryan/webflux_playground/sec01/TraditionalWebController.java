package com.dwikyryan.webflux_playground.sec01;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("traditional")
@Slf4j
public class TraditionalWebController {
    private final RestClient restClient = RestClient.builder()
    .requestFactory(new JdkClientHttpRequestFactory())
    .baseUrl("http://localhost:7070")
    .build();

    @GetMapping("products")
    public List<Product> getProducts() {
        var list = this.restClient.get()
                .uri("/demo1/products/notorious")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {

                });
        log.info("received response: {}", list);
        return list;       
    }

    // Not reactive programming
    @GetMapping("products")
    public Flux<Product> getProducts2() {
        var list = this.restClient.get()
                .uri("/demo1/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {

                });
        log.info("received response: {}", list);
        return Flux.fromIterable(list);       
    }
}
