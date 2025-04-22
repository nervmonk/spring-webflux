package com.dwikyryan.webflux_playground.tests.sec02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dwikyryan.webflux_playground.sec02.repository.CustomerOrderRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

@Slf4j
class Lec03CustomerOrderRepositoryTest extends AbstractTest {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Test
    void productsOrderedByCustomer() {
        this.customerOrderRepository.getProductsOrderedByCustomer("mike")
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    void orderDetailsByProduct() {
        this.customerOrderRepository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(dto -> log.info("{}", dto))
                .as(StepVerifier::create)
                .assertNext(dto -> Assertions.assertEquals(975, dto.amount()))
                .assertNext(dto -> Assertions.assertEquals(950, dto.amount()))
                .expectComplete()
                .verify();
    }

}
