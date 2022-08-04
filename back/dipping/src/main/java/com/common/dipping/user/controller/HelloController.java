package com.common.dipping.user.controller;

import com.common.dipping.user.dto.SignUpDto;
import com.common.dipping.utils.TokenUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController(value = "/hello")
public class HelloController {

    @GetMapping(value = "/good")
    public String signUp() {
        System.out.println("hello");
        return "hello";
    }
}
