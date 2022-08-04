package com.common.dipping.user.controller;

import com.common.dipping.user.domain.User;
import com.common.dipping.user.dto.LoginDto;
import com.common.dipping.user.dto.ProfileDto;
import com.common.dipping.user.dto.ProfileEditDto;
import com.common.dipping.user.dto.SignUpDto;
import com.common.dipping.user.service.UserService;
import com.common.dipping.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(signUpDto.getUserNickname());
        return userService.isEmailDuplicated(signUpDto.getEmail())
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(TokenUtils.generateJwtToken(userService.signUp(signUpDto)));
    }


    @GetMapping(value = "/profile")
    public ResponseEntity<?> profile(@Param("userNickname") String userNickname) {
        User userinfo = userService.profile(userNickname);
        if(userinfo == null) {
            return ResponseEntity.badRequest().build();
        }
//      본인 프로필일 시 비공개 상태이더라도 접속 가능
        if (!userinfo.getOpenUser()) {
            return ResponseEntity.ok().body("해당 유저는 비공개 상태입니다.");
        }

        ProfileDto profileDto = new ProfileDto();

        // set을 통해 profileDto에 데이터 설정
        profileDto.setEmail(userinfo.getEmail());
        profileDto.setUserNickname(userinfo.getUserNickname());
        profileDto.setProfileImgUrl(userinfo.getProfileImgUrl());
        profileDto.setUserMusicTaste(userinfo.getUserMusicTaste());
        profileDto.setProvider(userinfo.getProvider());
        profileDto.setMusicGerne(userinfo.getMusicGerne());
        // profileDto의 createdAt은 String인 반면 User는 LocalDateTime이여서 형변환이 필요하다.
        profileDto.setCreateAt(userinfo.getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss")));
        profileDto.setUpdateAt(userinfo.getUpdatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss")));
        profileDto.setOpenUser(userinfo.getOpenUser());

        // result는 code와 data는 key값이
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> userResult = new HashMap<>(); // user 라고 명시하기 위한 키값
        result.put("code", 200); // code : 200
        userResult.put("user", profileDto); // "user" : profileDto
        result.put("data", userResult);
        /*
        *  "data" : {
        *   "user" : {
        *       userEmail,
        *       }
        *   }
        * */

        return ResponseEntity.ok().body(result);
    }

    /* 프로필 수정
    * '{
    * "email": "",
    "userNickname":"",
    "profileImgUrl":"",
    "userMusicTaste":"",
    "openUser":""
    }'
    *
    * */
    @PostMapping(value = "/profile")
    public ResponseEntity<?> profileEdit(@RequestBody final ProfileEditDto profileEditDto) {
        // 이메일을 통해 유저 정보를 가져오고 가져온 정보랑 ProfileEditDto의 정보를 비교
        // 닉네임이 바뀔 경우
        // UserService에서 처리하자!
//        if(userService.isUserNicknameDuplicated(profileEditDto.getUserNickname())) {

//            System.out.println(profileEditDto.getEmail() == userService.profile(profileEditDto.getUserNickname()).getEmail());
//            if (profileEditDto.getEmail() != userService.profile(profileEditDto.getUserNickname()).getEmail()) {
//                return ResponseEntity.badRequest().build();
//            }
//        }

        if (userService.profileEdit(profileEditDto)) {
            return ResponseEntity.ok().body("수정 완료");
        } else {
            return ResponseEntity.ok().body("닉네임이 중복되었습니다.");
        }

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