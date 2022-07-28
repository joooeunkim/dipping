package com.common.dipping.user.controller;

import com.common.dipping.user.dto.LoginDto;
import com.common.dipping.user.dto.SignUpDto;
import com.common.dipping.user.service.UserService;
import com.common.dipping.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
@Log4j2
public class UserController {

    private final UserService userService;

//    @PostMapping(value = "/login")
//    public ResponseEntity<String> login(@RequestBody final LoginDto loginDto){
//        System.out.println(loginDto.getEmail());
//        System.out.println(loginDto.getPassword());
//        return userService.isEmailDuplicated(loginDto.getEmail())
//                ? ResponseEntity.ok(TokenUtils.generateJwtToken(userService.login(loginDto)))
//                : ResponseEntity.badRequest().build();
//    }

    @PostMapping(value = "/signUp")
    public ResponseEntity<String> signUp(@RequestBody final SignUpDto signUpDto) {
        System.out.println(signUpDto.getEmail());
        System.out.println(signUpDto.getPassword());
        System.out.println(signUpDto.getNickname());
        return userService.isEmailDuplicated(signUpDto.getEmail())
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(TokenUtils.generateJwtToken(userService.signUp(signUpDto)));
    }



//    @GetMapping(value = "/oauth/kakao")
//    public ResponseEntity<String> kakaoLogin() {
//
//    }

//    @GetMapping(value = "/oauth/google")
//    public ResponseEntity<String> googleLogin() {
//
//    }

}