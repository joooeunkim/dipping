package com.common.dipping.api.user.controller;

import com.common.dipping.api.board.domain.dto.ProfileDippingPostDto;
import com.common.dipping.api.board.domain.dto.ProfilePostDto;
import com.common.dipping.api.board.service.BoardService;
import com.common.dipping.api.dipping.service.DippingService;
import com.common.dipping.api.user.domain.dto.*;
import com.common.dipping.api.user.domain.entity.Code;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.service.CodeService;
import com.common.dipping.api.user.service.FollowService;
import com.common.dipping.api.user.service.StorageService;
import com.common.dipping.api.user.service.UserService;
import com.common.dipping.common.ApiResponse;
import com.common.dipping.common.ApiResponseType;
import com.common.dipping.jwt.JwtProvider;
import com.common.dipping.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final CodeService codeService;
    private final JwtProvider jwtProvider;
    private final BoardService boardService;
    private final DippingService dippingService;
    private final StorageService storageService;
    private final FollowService followService;

    @Autowired
    ResourceLoader resourceLoader;

    @Operation(summary = "디핑 회원가입", description = "디핑 계정으로 회원가입")
    @PostMapping(value = "/signUp")
    public ResponseEntity signUp(@RequestBody final SignUpDto signUpDto) {

        if(userService.isEmailDuplicated(signUpDto.getEmail())){ //이메일이 이미 존재
            return ResponseEntity.badRequest().body("이미 가입된 회원입니다");
        } else{
            User user = userService.signUp(signUpDto);
            userService.registerIntersetTag(user);
            return ResponseEntity.ok().build();
        }
    }

    //소셜 로그인 후 사용자가 제공받은 토큰을 헤더에 담고, 추가정보(이메일, 닉네임, 음악 장르, 음악 취향)를 body에 보내면
    //아직 user의 Role이 GUEST이기 때문에 서버는 이 요청을 회원가입의 연장으로 이해하여 추가정보를 저장하고 user의 Role를 USER로 변경 후 토큰을 재발행해준다.
    @Operation(summary = "소셜 회원가입", description = "소셜 회원가입 후 추가 입력 정보들을 담아 회원가입을 완료한다. 회원가입 후 사용자의 ROLE을 GUEST에서 USER로 업그레이드 되어 새로운 토큰을 재발행한다")
    @PostMapping(value="/signUp/info")
    public void signUpAddInfo(HttpServletResponse response, @AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody final SignUpDto signUpDto) throws IOException {

        User user = userService.signUpAddInfo(userInfo.getUsername(), userInfo.getProvider(), signUpDto);
        String token = jwtProvider.generateJwtToken(new UsernamePasswordAuthenticationToken(user.getEmail(), "", userInfo.getAuthorities()));
        ApiResponse.token(response, token);
    }

    @Operation(summary = "이메일 중복 확인", description = "이미 중복된 이메일이 있으면 true 반환")
    @GetMapping(value="/check/email")
    public boolean checkDuplicatedEmail(@RequestParam final String email){
        return userService.isEmailDuplicated(email);
    }

    @Operation(summary = "닉네임 중복 확인", description = "이미 중복된 닉네임이 있으면 true 반환")
    @GetMapping(value="/check/nickname")
    public boolean checkDuplicatedNickname(@RequestParam final String nickname){
        return userService.isUserNicknameDuplicated(nickname);
    }


    @Operation(summary = "비밀번호 찾기1", description = "사용자 이메일 입력시 해당 이메일로 비밀번호 변경 페이지 로드 할 수 있도록 함")
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

    @Operation(summary = "비밀번호 찾기2", description = "요청 param에 있는 인증 코드를 확인하여 사용자의 비밀번호를 변경해준다")
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


    @Operation(summary = "프로필 정보", description = "닉네임을 보낼 경우 닉네임의 사용자 프로필 정보, 닉네임이 없을 경우 자신의 프로필 정보 반환")
    @GetMapping(value = "/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal UserDetailsImpl requestUser, @RequestParam(name = "userNickname", required = false) String userNickname) {
        Boolean isMe = false;
        User userinfo;
        if(userNickname != null) {
            userinfo = userService.profile(userNickname);
            if (userinfo == null) {
                return ResponseEntity.badRequest().build();
            }
//      본인 프로필일 시 비공개 상태이더라도 접속 가능
            if (!userinfo.getOpenUser()) {
                return ResponseEntity.ok().body("해당 유저는 비공개 상태입니다.");
            }
        }else {
            isMe = true;
            userinfo = userService.profile(requestUser.getNickname());
        }
        Boolean isFollowed = followService.existFollowBySenderNicknameReceiverNickname(requestUser.getNickname(), userinfo.getNickname());
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
        profileDto.setBoardCount(userinfo.getBoards().size());
        profileDto.setFollowerCount(userService.followerCount(userinfo));
        profileDto.setFollowingCount(userService.followingCount(userinfo));
        profileDto.setIsMe(isMe);
        profileDto.setIsFollowed(isFollowed);

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
    @Operation(summary = "프로필 수정", description = "변경할 프로필 정보로 저장")
    @PostMapping(value = "/profile")
    public ResponseEntity<?> profileEdit(@RequestBody final ProfileEditDto profileEditDto) throws IOException {
        if (userService.profileEdit(profileEditDto)) {
            return ResponseEntity.ok().body("수정 완료");
        } else {
            return ResponseEntity.ok().body("닉네임이 중복되었습니다.");
        }
    }

    @Operation(summary = "프로필 이미지 저장", description = "변경할 프로필 이미지를 저장 후 저장 경로를 profileImgUrl에 저장")
    @PostMapping(value="/profile/upload")
    public ResponseEntity<?> profileImgUrlEdit(@AuthenticationPrincipal UserDetailsImpl userinfo, @RequestBody MultipartFile file) throws IOException {
        //FileUpload 관련 설정.
        if (file!=null) {
            // 파일을 저장할 폴더 지정
            Resource res = resourceLoader.getResource("/static/upload");
            System.out.println("res.exists(): "+res.exists());
            System.out.println("res.getDescription(): "+res.getDescription());
            System.out.println("res.getURI().getPath(): "+res.getURI().getPath());
            String canonicalPath = res.getFile().getCanonicalPath();
            //String canonicalPath = "home"+File.separator+"ubuntu"+File.separator+"S07P12B210"+File.separator+"back"+File.separator+"dipping"+File.separator+"build"+File.separator+"resources"+File.separator+"main"+File.separator+"static"+File.separator+"upload";
            System.out.println("file upload canonical path : "+ canonicalPath);
            File folder = new File(canonicalPath);
            if (!folder.exists()){
                folder.mkdirs();
            }
            String originalFileName = file.getOriginalFilename();
            String saveFileName = "untitled";
            if (!originalFileName.isEmpty()) {
                saveFileName = userinfo.getUsername() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                System.out.println("원본 파일 이름 : "+file.getOriginalFilename()+", 실제 저장 파일 이름 : "+ saveFileName);
                file.transferTo(new File(folder, saveFileName));
            }
            String newProfileImgUrl = canonicalPath + File.separator + saveFileName;

            if (userService.profileImgUrlEdit(userinfo, newProfileImgUrl)) {
                return ResponseEntity.ok().body("프로필 이미지 수정 완료");
            } else {
                return ResponseEntity.ok().body("프로필 이미지 수정 실패");
            }
        } else{
            return ResponseEntity.ok().body("변경할 프로필 이미지가 없습니다");
        }
    }

    @Operation(summary = "프로필 탭 조회", description = "프로필 전체 정보 반환(user, post, dipping, collection). 닉네임을 보낼 경우 닉네임의 사용자 프로필 전체 정보, 닉네임이 없을 경우 자신의 프로필 전체 정보 반환")
    @GetMapping(value="/profiles")
    public ResponseEntity<?> profileAll(@AuthenticationPrincipal UserDetailsImpl requestUser, @RequestParam(name = "userNickname", required = false) String userNickname) {
        Boolean isMe = false;

        User userinfo;
        if(userNickname != null) {
            userinfo = userService.profile(userNickname);
            if (userinfo == null) {
                return ResponseEntity.badRequest().build();
            }
//      본인 프로필일 시 비공개 상태이더라도 접속 가능
            if (!userinfo.getOpenUser()) {
                return ResponseEntity.ok().body("해당 유저는 비공개 상태입니다.");
            }
        }else {
            isMe = true;
            userinfo = userService.profile(requestUser.getNickname());
        }
        Boolean isFollowed = followService.existFollowBySenderNicknameReceiverNickname(requestUser.getNickname(), userinfo.getNickname());

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
        profileDto.setBoardCount(userinfo.getBoards().size());
        profileDto.setFollowerCount(userService.followerCount(userinfo));
        profileDto.setFollowingCount(userService.followingCount(userinfo));
        profileDto.setIsMe(isMe);
        profileDto.setIsFollowed(isFollowed);

        //사용자의 게시물 목록
        List<ProfilePostDto> boardPostDto = boardService.getAllBoardByUserId(userinfo.getId());

        //사용자의 디핑 목록
        List<ProfileDippingPostDto> dippingPostDto = dippingService.getAllDippingByUserId(userinfo.getId());

        //사용자의 보관함 목록
        List<ProfilePostDto> collectionPostDto = storageService.getAllStorageByUserId(userinfo.getId());


        // result는 code와 data는 key값이
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> dataResult = new HashMap<>(); // user 와 post 정보 담기
        result.put("code", 200); // code : 200
        dataResult.put("user", profileDto); // "user" : profileDto
        dataResult.put("post", boardPostDto); //"post" : 사용자의 포스트
        dataResult.put("dipping", dippingPostDto); //"dipping" : 사용자의 디핑
        dataResult.put("collection", collectionPostDto); //"collection" : 사용자의 보관함
        result.put("data", dataResult);
        /*
         *  "data" : {
         *   "user" : {
         *       userEmail,
         *       }
         *   },
         *   "post" : [
         *      {
         *         boardId,
         *         songImgUrl,
         *      },
         *      {
         *         boardId,
         *         songImgUrl,
         *      }
         *      ]
         *
         * */

        return ResponseEntity.ok().body(result);
    }

}