package com.common.dipping.api.search.controller;


import com.common.dipping.api.search.service.SearchService;
import com.common.dipping.api.user.domain.dto.MiniProfileDto;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.security.UserDetailsImpl;
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
}
