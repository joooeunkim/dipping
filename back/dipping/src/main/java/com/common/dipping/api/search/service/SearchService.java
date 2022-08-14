package com.common.dipping.api.search.service;

import com.common.dipping.api.board.domain.dto.BoardDto;
import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.PostTag;
import com.common.dipping.api.board.domain.entity.Tag;
import com.common.dipping.api.board.repository.PostTagRepository;
import com.common.dipping.api.board.repository.TagRepository;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class SearchService {

    @Autowired
    private final UserRepository userRepository;
    private final SearchRepository searchRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    public List<MiniProfileDto> searchUser(String keyword, UserDetailsImpl userDetails) {
        List<User> usersList = userRepository.findAllByNicknameContaining(keyword);
        List<MiniProfileDto> miniProfileDtos = new ArrayList<MiniProfileDto>();
        for (int i = 0; i < usersList.size(); i++) {
            MiniProfileDto userInfo = new MiniProfileDto();
            userInfo.setNickname(usersList.get(i).getNickname());
            userInfo.setProfileImgUrl(usersList.get(i).getProfileImgUrl());
            miniProfileDtos.add(userInfo);
        }
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        Search search = Search.builder()
                .word(keyword)
                .user(user)
                .build();
        searchRepository.save(search);
        return miniProfileDtos;
    }

    public HashSet<BoardDto> searchPost(String keyword, UserDetailsImpl userDetails) {
        List<Tag> tagList = tagRepository.findAllByContentContaining(keyword);
        HashSet<BoardDto> boardList = new HashSet<>();
        for (int i = 0; i < tagList.size(); i++) {
            List<PostTag> postTags = postTagRepository.findAllByTag(tagList.get(i));
            for (PostTag postTag:postTags){
                BoardDto board = new BoardDto();
                board.setId(postTag.getBoard().getId());
                board.setAlbumArt(postTag.getBoard().isAlbumArt());
                board.setContent(postTag.getBoard().getContent());
                board.setCreatedAt(postTag.getBoard().getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss")));
                board.setUpdatedAt(postTag.getBoard().getUpdatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss")));
                board.setOpenComment(postTag.getBoard().isOpenComment());
                board.setOpenPost(postTag.getBoard().isOpenPost());
                board.setUserId(postTag.getBoard().getUser().getId());
                boardList.add(board);
            }
        }
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        Search search = Search.builder()
                .word(keyword)
                .user(user)
                .build();
        searchRepository.save(search);
        return boardList;
    }

}
