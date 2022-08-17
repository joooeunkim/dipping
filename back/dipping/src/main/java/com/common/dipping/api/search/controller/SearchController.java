package com.common.dipping.api.search.controller;


import com.common.dipping.api.board.domain.dto.BoardDto;
import com.common.dipping.api.board.domain.dto.ProfilePostDto;
import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.dipping.domain.dto.DippingResponseDto;
import com.common.dipping.api.search.service.SearchService;
import com.common.dipping.api.user.domain.dto.MiniProfileDto;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/search")
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService;

    /*
    {
      "code": "200",
      "data": [
        "users": [
          {
            "nickname": "ba_2_h",
            "profileImgUrl": "/profile.png"
          }
        ],
        "boards": [
            {
                BoardDto boardDto1
            },
            {
                BoardDto boardDto2
            }
        ]
      ]
    }
    */
    @Operation(summary = "검색 페이지 탭", description = "검색 페이지 탭을 눌렀을 때 추천 사용자와 추천 포스트  목록들을 반환")
    @GetMapping("/")
    public ResponseEntity<?> searchPage(@AuthenticationPrincipal UserDetailsImpl userInfo) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        List<MiniProfileDto> userList = searchService.searchRecommendedUser(userInfo);
        List<ProfilePostDto> boardList = searchService.searchRecommendedBoards(userInfo);
        result.put("code", 200);
        data.put("users", userList);
        data.put("boards", boardList);
        result.put("data", data);
        return ResponseEntity.ok().body(result);
    }

    /*
    {
      "code": "200",
      "data": {
        "users": [
          {
            "nickname": "ba_2_h",
            "profileImgUrl": "/profile.png"
          }
        ]
      }
    }
    */
    @Operation(summary = "사용자 검색", description = "키워드를 포함하는 닉네임을 가진 사용자 목록을 반환")
    @GetMapping("/user")
    public ResponseEntity<?> searchUser(@AuthenticationPrincipal UserDetailsImpl userInfo, @Param("keyword") String keyword) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> searchResult = new HashMap<>();

        List<MiniProfileDto> userList = searchService.searchUser(keyword, userInfo);
        result.put("code", 200);
        searchResult.put("users", userList);
        result.put("data", searchResult);

        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "포스트 검색", description = "키워드를 포함하는 포스트 태그를 포함한 포스트 목록을 반환")
    @GetMapping("/post")
    public ResponseEntity<?> searchPost(@AuthenticationPrincipal UserDetailsImpl userInfo, @Param("keyword") String keyword) {
        Map<String,Object> result = new HashMap<>();
        Map<String,HashSet> searchResult = new HashMap<>();

        HashSet<BoardDto> boardList = searchService.searchPost(keyword, userInfo);
        result.put("code", 200);
        searchResult.put("posts", boardList);
        result.put("data", searchResult);

        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "디핑 검색", description = "키워드를 포함하는 제목의 디핑 목록을 반환")
    @GetMapping("/dipping")
    public ResponseEntity<?> searchDipping(@AuthenticationPrincipal UserDetailsImpl userInfo, @Param("keyword") String keyword) {
        Map<String,Object> result = new HashMap<>();
        Map<String,List> searchResult = new HashMap<>();

        List<DippingResponseDto> dippingList = searchService.searchDipping(keyword, userInfo);
        result.put("code", 200);
        searchResult.put("dippings", dippingList);
        result.put("data", searchResult);

        return ResponseEntity.ok().body(result);

    }
}
