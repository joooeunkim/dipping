package com.common.dipping.api.user.controller;

import com.common.dipping.api.user.domain.dto.*;
import com.common.dipping.api.user.domain.entity.Code;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.service.CodeService;
import com.common.dipping.api.user.service.UserService;
import com.common.dipping.common.ApiResponse;
import com.common.dipping.common.ApiResponseType;
import com.common.dipping.jwt.JwtProvider;
import com.common.dipping.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final CodeService codeService;
    private final JwtProvider jwtProvider;

    @PostMapping(value = "/signUp")
    public ResponseEntity signUp(@RequestBody final SignUpDto signUpDto) {

        if(userService.isEmailDuplicated(signUpDto.getEmail())){ //이메일이 이미 존재
            return ResponseEntity.badRequest().body("이미 가입된 회원입니다");
        } else{
            userService.signUp(signUpDto);
            return ResponseEntity.ok().build();
        }
    }

    //소셜 로그인 후 사용자가 제공받은 토큰을 헤더에 담고, 추가정보(이메일, 닉네임, 음악 장르, 음악 취향)를 body에 보내면
    //아직 user의 Role이 GUEST이기 때문에 서버는 이 요청을 회원가입의 연장으로 이해하여 추가정보를 저장하고 user의 Role를 USER로 변경 후 토큰을 재발행해준다.
    @PostMapping(value="/signUp/info")
    public void signUpAddInfo(HttpServletResponse response, @AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody final SignUpDto signUpDto) throws IOException {

        User user = userService.signUpAddInfo(userInfo.getUsername(), userInfo.getProvider(), signUpDto);
        String token = jwtProvider.generateJwtToken(new UsernamePasswordAuthenticationToken(user.getEmail(), "", userInfo.getAuthorities()));
        ApiResponse.token(response, token);
    }


    @PostMapping(value="/findpw/sendEmail")
    public ResponseEntity<?> sendEmailToFindPw(@RequestBody LoginDto loginDto) throws MessagingException {
        Map<String, Object> result = new HashMap<>();

        if(userService.isEmailDuplicated(loginDto.getEmail())){ //회원정보 존재
            if(userService.isProviderDipping(loginDto.getEmail())){//디핑으로 회원가입한 경우
                MailDto mailDto = userService.createMailWithTempPassword(loginDto.getEmail());
                userService.sendMail(mailDto);
                result.put("code", ApiResponseType.SUCCESS.getCode());
            } else{//소셜로그인한 경우
                result.put("code", ApiResponseType.NOT_VALID_RESPONSE.getCode());
                result.put("msg","카카오 또는 구글 계정이 존재합니다");
            }
        } else{ //회원가입 하지 않은 경우
            result.put("code", ApiResponseType.NOT_FOUND_DATA_RESPONSE.getCode());
            result.put("msg","계정이 존재하지 않습니다. 회원가입 해주세요");
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value="/findpw/reset")
    public ResponseEntity<?> updateNewPassword(@RequestParam("code") String code, @RequestBody LoginDto loginDto) throws MessagingException {
        Map<String, Object> result = new HashMap<>();

        Code codeInfo = codeService.findByCode(code);
        userService.updatePassword(codeInfo.getUser().getEmail(), loginDto.getPassword());
        result.put("code", ApiResponseType.SUCCESS.getCode());

        return ResponseEntity.ok().body(result);
    }

//    @PostMapping(value="/newpw")
//    public ResponseEntity<?> updateNewPassword(@RequestBody LoginDto loginDto) throws MessagingException {
//        Map<String, Object> result = new HashMap<>();
//
//        if(userService.isEmailDuplicated(loginDto.getEmail())){ //회원정보 존재
//            if(userService.isProviderDipping(loginDto.getEmail())){//디핑으로 회원가입한 경우
//                userService.updatePassword(loginDto.getEmail(), loginDto.getPassword());
//                result.put("code", ApiResponseType.SUCCESS.getCode());
//            } else{//소셜로그인한 경우
//                result.put("code", ApiResponseType.NOT_VALID_RESPONSE.getCode());
//                result.put("msg","카카오 또는 구글 계정이 존재합니다");
//            }
//        } else{ //회원가입 하지 않은 경우
//            result.put("code", ApiResponseType.NOT_FOUND_DATA_RESPONSE.getCode());
//            result.put("msg","계정이 존재하지 않습니다. 회원가입 해주세요");
//        }
//        return ResponseEntity.ok().body(result);
//    }


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