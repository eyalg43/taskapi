package com.example.taskapi.contoller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello world from spring boot!";
    }

    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "Guest") String name) {
        return "Greetings, " + name +"!";
    }

    @PostMapping("/echo")
    public String echo(@RequestBody String message) {
        return "You said: " + message;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to task API!";
    }
}
