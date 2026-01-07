package com.omar.demoapi.controller;

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

//    @GetMapping("/greet")
//    public String greetWithParam(
//            @RequestParam(required = false) String name
//    ) {
//        if (name == null) {
//            return "Hello!";
//        }
//        return "Hello " + name;
//    }

    @GetMapping("/greet")
    public String greetWithParam(
            @RequestParam(defaultValue = "user") String name
    ) {
        return "Hello " + name;
    }


}
