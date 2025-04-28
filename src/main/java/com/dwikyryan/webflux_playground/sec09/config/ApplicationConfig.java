package com.dwikyryan.webflux_playground.sec09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dwikyryan.webflux_playground.sec09.dto.ProductDto;

import reactor.core.publisher.Sinks;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public Sinks.Many<ProductDto> sink(){
        return Sinks.many().replay().limit(1);
    }
}
