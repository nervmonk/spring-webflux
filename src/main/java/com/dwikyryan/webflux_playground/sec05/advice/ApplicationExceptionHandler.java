package com.dwikyryan.webflux_playground.sec05.advice;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dwikyryan.webflux_playground.sec05.exceptions.CustomerNotFoundException;
import com.dwikyryan.webflux_playground.sec05.exceptions.InvalidInputException;

@ControllerAdvice
public class ApplicationExceptionHandler {
    
    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException ex){
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("http://example.com/problems/customer-not-found"));
        problem.setTitle("Customer Not Found");
        return problem;
    }

    @ExceptionHandler(InvalidInputException.class)
    public ProblemDetail handleException(InvalidInputException ex){
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setType(URI.create("http://example.com/problems/invalid-input"));
        problem.setTitle("Invalid Input");
        return problem;
    }
}
