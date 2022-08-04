package com.common.dipping.user.service;

import com.common.dipping.enums.UserRole;
import com.common.dipping.user.domain.User;
import com.common.dipping.user.dto.LoginDto;
import com.common.dipping.user.dto.SignUpDto;
import com.common.dipping.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .build();

        return userRepository.save(user);
    }

    public boolean isEmailDuplicated(final String email) {
        return userRepository.existsByEmail(email);
    }

//    public User login(LoginDto loginDto) {
//        return userRepository.findByEmailAndPassword(loginDto.getEmail(), passwordEncoder.encode(loginDto.getPassword()));
//    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}