package com.shinkle.userapi.greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public Greeting getAndIncGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), format("Hello, %s!", name));
    }
}
