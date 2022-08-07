package com.common.dipping.api.user.service;

import com.common.dipping.common.UserRole;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.domain.dto.ProfileEditDto;
import com.common.dipping.api.user.domain.dto.SignUpDto;
import com.common.dipping.api.user.repository.UserRepository;
import com.common.dipping.security.UserDetailsImpl;
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

    public boolean isUserNicknameDuplicated(final String userNickname) {
        return userRepository.existsByNickname(userNickname);
    }

//    public User login(LoginDto loginDto) {
//        return userRepository.findByEmailAndPassword(loginDto.getEmail(), passwordEncoder.encode(loginDto.getPassword()));
//    }

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

}