package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {
    @Get()
    public HttpResponse<?> greet() {
        return HttpResponse.status(HttpStatus.OK).body("Hallo Welt");
    }
}