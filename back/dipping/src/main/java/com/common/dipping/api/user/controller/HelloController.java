package com.common.dipping.api.user.controller;

import com.common.dipping.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK !!"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
})

@RestController
@RequestMapping(value = "/api")
public class HelloController {

    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("성공적");
    }

    @GetMapping(value = "/hi")
    public ResponseEntity<String> hello2(@AuthenticationPrincipal UserDetailsImpl userInfo){
        //@AuthenticationPrincipal 어노테이션을 UserDetailsImpl과 함께 사용하면 현재 토큰에 저장되어있는 사용자 정보를 다음과 같이 추출할 수 있습니다!
        System.out.println(userInfo.getUsername());//이메일
        System.out.println(userInfo.getNickname());//닉네임
        System.out.println(userInfo.getId());//사용자 고유번호
        System.out.println(userInfo.getAuthorities());//권한

        return ResponseEntity.ok(userInfo.toString());
    }
}
