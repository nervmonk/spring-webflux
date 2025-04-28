package com.dwikyryan.webflux_playground.sec09.service;

import java.time.Duration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.dwikyryan.webflux_playground.sec09.dto.ProductDto;

import io.netty.util.internal.ThreadLocalRandom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    private final ProductService productService;

    public DataSetupService(ProductService productService){
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        Flux.range(1, 1000)
        .delayElements(Duration.ofSeconds(1))
        .map(i -> new ProductDto(null, "product-" + i, ThreadLocalRandom.current().nextInt(1, 100)))
        .flatMap(dto -> this.productService.saveProducts(Mono.just(dto)))
        .subscribe();
    }
    
}
