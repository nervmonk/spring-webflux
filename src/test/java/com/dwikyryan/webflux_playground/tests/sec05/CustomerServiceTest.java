package com.dwikyryan.webflux_playground.tests.sec05;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.dwikyryan.webflux_playground.sec05.dto.CustomerDto;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec05")
class CustomerServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void unauthorized() {
        // no token
        this.webTestClient.get()
                .uri("/customers")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        // invalid token
        this.validateGet("secret", HttpStatus.UNAUTHORIZED);
    }

    @Test
    void standardCategory(){
        this.validateGet("secret123", HttpStatus.OK);
        this.validatePost("secret123", HttpStatus.FORBIDDEN);
    }

    @Test
    void primeCategory(){
        this.validateGet("secret456", HttpStatus.OK);
        this.validatePost("secret456", HttpStatus.OK);
    }

    private void validateGet(String token, HttpStatus expectedStatus) {
        this.webTestClient.get()
                .uri("/customers")
                .header("auth-token", token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    private void validatePost(String token, HttpStatus expectedStatus) {
        var dto = new CustomerDto(null, "marshal", "marshal@gmail.com");
        this.webTestClient.post()
                .uri("/customers")
                .bodyValue(dto)
                .header("auth-token", token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }
}
