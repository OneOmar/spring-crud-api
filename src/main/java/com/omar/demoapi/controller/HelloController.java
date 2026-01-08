package com.omar.demoapi.controller;

import com.omar.demoapi.dto.HelloRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

//    @GetMapping
//    public String sayHello() { return "Hello!"; }

    @GetMapping("/{name}")
    public String helloWithName(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("/greet")
    public String greetWithParam(
            @RequestParam(defaultValue = "user") String name
    ) {
        return "Hello " + name;
    }


//    @PostMapping
//    public String helloPost(@RequestBody HelloRequest request) {
//        return "Hello " + request.getName() + ", age " + request.getAge();
//    }

    // Examples of using ResponseEntity
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from Spring Boot API!");
    }

    @PostMapping
    public ResponseEntity<String> helloPost(@RequestBody HelloRequest request) {

        if(request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("Name is required");
        }

        String response = "Hello " + request.getName() + ", age " + request.getAge();
        return ResponseEntity.status(201).body(response);
    }

}
