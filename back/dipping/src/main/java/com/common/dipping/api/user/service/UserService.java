package com.common.dipping.api.user.service;

import com.common.dipping.api.board.domain.dto.PostTagDto;
import com.common.dipping.api.board.domain.dto.UserTagDto;
import com.common.dipping.api.board.domain.entity.*;
import com.common.dipping.api.board.repository.BoardRepository;
import com.common.dipping.api.board.repository.InterestTagRepository;
import com.common.dipping.api.board.repository.TagRepository;
import com.common.dipping.api.board.repository.UserTagRepository;
import com.common.dipping.api.user.domain.dto.MailDto;
import com.common.dipping.api.user.domain.entity.Code;
import com.common.dipping.api.user.repository.CodeRepository;
import com.common.dipping.api.user.repository.FollowRepository;
import com.common.dipping.common.ApplicationYamlRead;
import com.common.dipping.common.UserRole;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.domain.dto.ProfileEditDto;
import com.common.dipping.api.user.domain.dto.SignUpDto;
import com.common.dipping.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApplicationYamlRead applicationYamlRead;
    private final JavaMailSender javaMailSender;
    private final FollowRepository followRepository;
    private final TagRepository tagRepository;
    private final InterestTagRepository interestTagRepository;
    private final BoardRepository boardRepository;


    @Transactional
    public User signUp(final SignUpDto signUpDto) {
        User user = User.builder()
                .email(signUpDto.getEmail())
                .nickname(signUpDto.getNickname())
                .pw(passwordEncoder.encode(signUpDto.getPassword()))
                .role(UserRole.ROLE_USER)
                .provider(signUpDto.getProvider())
                .profileImgUrl(signUpDto.getProfileImgUrl())
                .musicTaste(signUpDto.getMusicTaste())
                .musicGenre(signUpDto.getMusicGenre())
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User signUpAddInfo(String email, String provider,  SignUpDto signUpDto){
        User user = userRepository.findByEmailAndProvider(email, provider).orElse(null);
        user.signUpAddInfo(UserRole.ROLE_USER, signUpDto.getNickname(), signUpDto.getMusicTaste(), signUpDto.getProfileImgUrl(), signUpDto.getMusicGenre());
        return userRepository.save(user);
    }

    public boolean isEmailDuplicated(final String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isProviderDipping(final String email) {
        return userRepository.existsByEmailAndProvider(email, "dipping");
    }

    public MailDto createMailWithTempPassword(String email) {

        String tempPw = getTempPassword();

        MailDto mailDto = new MailDto();
        mailDto.setEmail(email);
        mailDto.setTitle("Dipping 임시 비밀번호 안내 이메일입니다.");
        //mailDto.setMessage("안녕하세요. Dipping 임시비밀번호 안내 이메일 입니다.\n 회원님의 임시 비밀번호는 [ " + tempPw + " ]입니다.\n 로그인 후에 비밀번호를 변경 바랍니다.");
        mailDto.setMessage("안녕하세요. Dipping 임시비밀번호 안내 이메일 입니다.\n다음 링크를 통하여 비밀번호 재설정 페이지로 이동할 수 있습니다.\nhttp://localhost:8080/setPassword?code="+tempPw+"\n링크에 접속하여 새로운 비밀번호를 설정하세요.");


        //updatePassword(email, tempPw); //임시비밀번호로 업데이트

        codeRepository.save(Code.builder().code(tempPw).user(userRepository.findByEmail(email).orElse(null)).build()); //사용자에게 생성된 code 저장

        return mailDto;
    }

    public void updatePassword(String email, String newPw ){
        User user = userRepository.findByEmail(email).orElse(null);
        if(user!=null){
            user.newPassword(passwordEncoder.encode(newPw));
            userRepository.save(user);
        }
    }

    public void sendMail(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getEmail());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom(applicationYamlRead.getUsername());
        message.setReplyTo(mailDto.getEmail());
        javaMailSender.send(message);
    }

    //랜덤함수로 임시비밀번호 구문 만들기
    public String getTempPassword(){
//        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
//                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

        String str = "";
        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }




    public boolean isUserNicknameDuplicated(final String userNickname) {
        return userRepository.existsByNickname(userNickname);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User profile(String userNickname) {
        User userinfo = userRepository.findAllByNickname(userNickname).orElse(null);
        return userinfo;
    }

    // set을 안 쓰고 바꾸기 update 함수 만들기
    @Transactional
    public Boolean profileEdit(final ProfileEditDto profileEditDto) {

        User userinfo = userRepository.findByEmail(profileEditDto.getEmail()).orElse(null);
        User nicknameUser = userRepository.findByNickname(profileEditDto.getNickname()).orElse(null);
        if (nicknameUser != null && nicknameUser.getEmail() != userinfo.getEmail()) {
            return false;
        }

        userinfo.profileEdit(profileEditDto.getNickname(), profileEditDto.getProfileImgUrl(), profileEditDto.getMusicTaste(), profileEditDto.getOpenUser(), profileEditDto.getMusicGenre());

        userRepository.save(userinfo);
        return true;
    }

    public Long followingCount(User user) {
        return followRepository.countAllBySender(user);
    }

    public Long followerCount(User user) {
        return followRepository.countAllByReceiver(user);
    }

    public void registerIntersetTag(User user){

        String[] IntersetList;
        if(!user.getMusicTaste().isEmpty()) {
            String s = user.getMusicTaste().substring(1);
            IntersetList = s.split("#");
        }else {
            return;
        }
        System.out.println(Arrays.toString(IntersetList));

        if (IntersetList.length != 0) {
            for (int i = 0; i < IntersetList.length; i++) {
                Tag tag = tagRepository.findByContent(IntersetList[i]);
                if(tag == null) {
                    tag = Tag.builder().content(IntersetList[i]).build();
                    tag = tagRepository.save(tag);;
                }
                InterestTag interestTag = InterestTag.builder().tag(tag).user(user).build();
                interestTagRepository.save(interestTag);
            }
        }
    }

    public User findByBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            return null;
        }

        User user = userRepository.findByBoards(board);
        return user;

    }

}