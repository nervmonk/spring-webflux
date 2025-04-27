package com.dwikyryan.webflux_playground.tests.sec08;

import java.nio.file.Path;
import java.time.Duration;

import org.junit.jupiter.api.Test;

import com.dwikyryan.webflux_playground.sec08.dto.ProductDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
class ProductsUploadDownloadTest {
    private final ProductClient productClient = new ProductClient();

    @Test
    void upload() {
        var flux = Flux.range(1, 1_000)
                .map(i -> new ProductDto(null, "product-" + i, i));

        this.productClient.uploadProducts(flux)
                .doOnNext(r -> log.info("received: {}", r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void download() {
        this.productClient.downloadProducts()
                .map(ProductDto::toString)
                .as(flux -> FileWriter.create(flux, Path.of("products.txt")))
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
