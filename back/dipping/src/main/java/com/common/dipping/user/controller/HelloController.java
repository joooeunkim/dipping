package com.common.dipping.user.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController(value = "/hello")
public class HelloController {

    @GetMapping(value = "/good")
    public String signUp() {
        System.out.println("hello");
        return "hello";
    }
}
