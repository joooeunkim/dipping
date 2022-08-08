package com.common.dipping.api.user.controller;

import com.common.dipping.api.user.domain.dto.FollowDto;
import com.common.dipping.api.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody FollowDto followDto) {
        // 본인에게 보내는 팔로잉 차단
        if (followDto.getSenderNickname().equals(followDto.getReceiverNickname())) {
            return ResponseEntity.badRequest().build();
        }
        // 팔로우 번호를 가져오고 없을 경우 -1을 리턴
        Long id = followService.getFollowIdByFromEmailToEmail(followDto.getSenderNickname(), followDto.getReceiverNickname());
        if (id == -1) {return ResponseEntity.ok().body("팔로잉");}
        else if (id == -2) {return ResponseEntity.ok().body("존재하지 않는 유저입니다.");}
        return ResponseEntity.ok().body("언팔로잉");
    }

//    @DeleteMapping("/follow")
//    public void unFollowUser(@RequestBody FollowDto followDto) {
//        Long id = followService.getFollowIdByFromEmailToEmail(followDto.getFromUser(), followDto.getToUser());
//        followService.unFollow(id);
//    }
}
