package com.omar.demoapi.controller;

import com.omar.demoapi.dto.HelloRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "Hello!";
    }

    @GetMapping("/{name}")
    public String helloWithName(@PathVariable String name) {
        return "Hello " + name;
    }

//    @GetMapping("/greet")
//    public String helloWithParam(@RequestParam String name) {
//        return "Hello " + name;
//    }

    @GetMapping("/greet")
    public String greetWithParam(
            @RequestParam(defaultValue = "user") String name
    ) {
        return "Hello " + name;
    }


    @PostMapping
    public String helloPost(@RequestBody HelloRequest request) {
        return "Hello " + request.getName() + ", age " + request.getAge();
    }

}
