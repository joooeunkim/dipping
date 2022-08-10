package com.common.dipping.api.search.service;

import com.common.dipping.api.search.domain.entity.Search;
import com.common.dipping.api.search.repository.SearchRepository;
import com.common.dipping.api.user.domain.dto.MiniProfileDto;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import com.common.dipping.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class SearchService {

    @Autowired
    private final UserRepository userRepository;
    private final SearchRepository searchRepository;

    public List<MiniProfileDto> searchUser(String keyword, UserDetailsImpl user) {
        List<User> usersList = userRepository.findAllByNicknameContaining(keyword);
        List<MiniProfileDto> miniProfileDtos = new ArrayList<MiniProfileDto>();
        for (int i = 0; i < usersList.size(); i++) {
            MiniProfileDto userInfo = new MiniProfileDto();
            userInfo.setNickname(usersList.get(i).getNickname());
            userInfo.setProfileImgUrl(usersList.get(i).getProfileImgUrl());
            miniProfileDtos.add(userInfo);
        }
        User userId = userRepository.findById(user.getId()).orElse(null);
        Search search = Search.builder()
                .word(keyword)
                .userId(userId)
                .build();
        searchRepository.save(search);
        return miniProfileDtos;
    }

}
