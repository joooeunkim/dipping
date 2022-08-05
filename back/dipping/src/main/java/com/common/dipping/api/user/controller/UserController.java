package com.common.dipping.api.user.controller;

import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.domain.dto.ProfileDto;
import com.common.dipping.api.user.domain.dto.ProfileEditDto;
import com.common.dipping.api.user.domain.dto.SignUpDto;
import com.common.dipping.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signUp")
    public ResponseEntity signUp(@RequestBody final SignUpDto signUpDto) {

        if(userService.isEmailDuplicated(signUpDto.getEmail())){ //이메일이 이미 존재
            return ResponseEntity.badRequest().body("이미 가입된 회원입니다");
        } else{
            userService.signUp(signUpDto);
            return ResponseEntity.ok().build();
        }
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
        profileDto.setNickname(userinfo.getNickname());
        profileDto.setProfileImgUrl(userinfo.getProfileImgUrl());
        profileDto.setMusicTaste(userinfo.getMusicTaste());
        profileDto.setProvider(userinfo.getProvider());
        profileDto.setMusicGenre(userinfo.getMusicGenre());
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