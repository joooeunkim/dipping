package com.common.dipping.user.service;

import com.common.dipping.enums.UserRole;
import com.common.dipping.user.domain.User;
import com.common.dipping.user.dto.LoginDto;
import com.common.dipping.user.dto.ProfileDto;
import com.common.dipping.user.dto.ProfileEditDto;
import com.common.dipping.user.dto.SignUpDto;
import com.common.dipping.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User signUp(final SignUpDto signUpDto) {
        final User user = User.builder()
                .email(signUpDto.getEmail())
                .userNickname(signUpDto.getUserNickname())
                .pw(passwordEncoder.encode(signUpDto.getPassword()))
                .role(UserRole.ROLE_USER)
                .provider(signUpDto.getProvider())
                .profileImgUrl(signUpDto.getProfileImgUrl())
                .userMusicTaste(signUpDto.getUserMusicTaste())
                .musicGerne(signUpDto.getMusicGerne())
                .build();

        return userRepository.save(user);
    }

    public boolean isEmailDuplicated(final String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isUserNicknameDuplicated(final String userNickname) {
        return userRepository.existsByUserNickname(userNickname);
    }

//    public User login(LoginDto loginDto) {
//        return userRepository.findByEmailAndPassword(loginDto.getEmail(), passwordEncoder.encode(loginDto.getPassword()));
//    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User profile(String userNickname) {
        User userinfo = userRepository.findAllByUserNickname(userNickname).orElse(null);
        return userinfo;
    }

    // set을 안 쓰고 바꾸기 update 함수 만들기
    @Transactional
    public User profileEdit(final ProfileEditDto profileEditDto) {

        User userinfo = userRepository.findByEmail(profileEditDto.getEmail()).orElse(null);

        // this.userNickname = profileEditDto.getUserNickname()
        userinfo.setUserNickname(profileEditDto.getUserNickname());
        userinfo.setProfileImgUrl(profileEditDto.getProfileImgUrl());
        userinfo.setUserMusicTaste(profileEditDto.getUserMusicTaste());
        userinfo.setOpenUser(profileEditDto.getOpenUser());
        // System.out.println(userinfo.getUserNickname());
        userRepository.save(userinfo);
        return userinfo;
    }

}