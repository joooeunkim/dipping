package com.common.dipping.user.domain.entity;

import com.common.dipping.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
// 인증 과정 중 실제 Database에 회원을 데이터를 조회하는UserDetailsService를 구현
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Not Found User"));
        return new UserDetailsImpl(
                user.getEmail(),
                user.getPw(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}