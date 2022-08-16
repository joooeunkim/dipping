package com.common.dipping.api.search.service;

import com.common.dipping.api.board.domain.dto.BoardDto;
import com.common.dipping.api.board.domain.dto.ProfilePostDto;
import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.InterestTag;
import com.common.dipping.api.board.domain.entity.PostTag;
import com.common.dipping.api.board.domain.entity.Tag;
import com.common.dipping.api.board.repository.PostTagRepository;
import com.common.dipping.api.board.repository.TagRepository;
import com.common.dipping.api.dipping.domain.dto.DippingResponseDto;
import com.common.dipping.api.dipping.domain.entity.Dipping;
import com.common.dipping.api.dipping.repository.DippingRepository;
import com.common.dipping.api.search.domain.entity.Search;
import com.common.dipping.api.search.repository.SearchRepository;
import com.common.dipping.api.user.domain.dto.MiniProfileDto;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import com.common.dipping.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class SearchService {

    private final UserRepository userRepository;
    private final SearchRepository searchRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final DippingRepository dippingRepository;

    public List<MiniProfileDto> searchUser(String keyword, UserDetailsImpl userDetails) {
        List<User> usersList = userRepository.findAllByNicknameContaining(keyword);
        List<MiniProfileDto> miniProfileDtos = new ArrayList<MiniProfileDto>();
        for (User user: usersList) {
            MiniProfileDto userInfo = new MiniProfileDto(user);
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
                BoardDto board = new BoardDto(postTag.getBoard());
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

    public List<DippingResponseDto> searchDipping(String keyword, UserDetailsImpl userDetails) {
        List<Dipping> dippingList = dippingRepository.findAllByDippingTitleContaining(keyword);
        List<DippingResponseDto> dippingResponseDtoList = new ArrayList<>();
        for (Dipping dipping: dippingList) {
            DippingResponseDto dippingResponseDto = new DippingResponseDto(dipping);
            dippingResponseDtoList.add(dippingResponseDto);
        }
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        Search search = Search.builder()
                .word(keyword)
                .user(user)
                .build();
        searchRepository.save(search);
        return dippingResponseDtoList;
    }

    public List<MiniProfileDto> searchRecommendedUser(UserDetailsImpl userDetails) {
        User userInfo = userRepository.findById(userDetails.getId()).orElse(null);
        List<User> users = userRepository.findAll();
        Map<User, Double> result = new HashMap<>();
        for (User user: users) {
            if (user.getMusicGenre() == null) {continue;}
            if (user.getId() == userInfo.getId()) {continue;}
            result.put(user, recommendScore(userInfo.getMusicGenre(),user.getMusicGenre()));
        }
        List<User> keySetList = new ArrayList<>(result.keySet());
        Collections.sort(keySetList, (o1, o2) -> (result.get(o2).compareTo(result.get(o1))));
        List<User> userList = keySetList.subList(0, (keySetList.size() > 5) ? 6: keySetList.size());
        List<MiniProfileDto> miniProfileDtos = new ArrayList<>();
        for (User user: userList) {
            MiniProfileDto miniProfileDto = new MiniProfileDto(user);
            miniProfileDtos.add(miniProfileDto);
        }
        return miniProfileDtos;
    }

    private static double recommendScore(String s1, String s2) {
        List<String> user1Genre = Arrays.asList(s1.substring(1).split("#"));
        List<String> user2Genre = Arrays.asList(s2.substring(1).split("#"));
        int score = 0;
        for (String genre : user1Genre) {
            if (user2Genre.contains(genre)) {
                score++;
            }
        }
        return score/Math.max(user1Genre.size(), user2Genre.size());
    }

    public List<ProfilePostDto> searchRecommendedBoards(UserDetailsImpl userDetails) {
        User userInfo = userRepository.findById(userDetails.getId()).orElse(null);
        List<ProfilePostDto> boards = new ArrayList<>();
        List<InterestTag>interestTags = userInfo.getInterestTags();
        for (InterestTag interestTag: interestTags) {
            List<PostTag> postTags = postTagRepository.findAllByTag(interestTag.getTag());
            for (PostTag postTag: postTags) {
                if(postTag.getBoard().getUser() == userInfo) {continue;}
                boards.add(new ProfilePostDto(postTag.getBoard()));
            }
        }
        return boards;
    }

}
