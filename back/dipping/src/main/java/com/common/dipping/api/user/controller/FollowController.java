package com.common.dipping.api.user.controller;

import com.common.dipping.api.alarm.service.AlarmService;
import com.common.dipping.api.user.domain.dto.FollowDto;
import com.common.dipping.api.user.domain.dto.FollowerListDto;
import com.common.dipping.api.user.domain.dto.FollowingListDto;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.service.FollowService;
import com.common.dipping.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class FollowController {

    private final FollowService followService;
    private final AlarmService alarmService;
    private final UserService userService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody FollowDto followDto) {
        // 본인에게 보내는 팔로잉 차단
        if (followDto.getSenderNickname().equals(followDto.getReceiverNickname())) {
            return ResponseEntity.badRequest().build();
        }
        // 팔로우 번호를 가져오고 없을 경우 -1을 리턴
        Long id = followService.getFollowIdByFromEmailToEmail(followDto.getSenderNickname(), followDto.getReceiverNickname());
        if (id == -1) {
            User receiver = userService.profile(followDto.getReceiverNickname());
            User sender = userService.profile(followDto.getSenderNickname());
            String alarmType = "follow";
            alarmService.alarmBySenderIdAndReceiverIdAndAlarmType(sender.getId(), receiver.getId(), alarmType);
            return ResponseEntity.ok().body("팔로잉");
        }
        else if (id == -2) {return ResponseEntity.ok().body("존재하지 않는 유저입니다.");}
        return ResponseEntity.ok().body("언팔로잉");
    }

//    @DeleteMapping("/follow")
//    public void unFollowUser(@RequestBody FollowDto followDto) {
//        Long id = followService.getFollowIdByFromEmailToEmail(followDto.getFromUser(), followDto.getToUser());
//        followService.unFollow(id);
//    }
    @GetMapping("/follow")
    public ResponseEntity<?> followList(@Param("nickname") String nickname) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> followResult = new HashMap<>();
        List<FollowingListDto> followingList = followService.getFollowListBySenderNickname(nickname);
        List<FollowerListDto> followerList = followService.getFollowListByReceiverNickname(nickname);
        result.put("code", 200); // code : 200
        followResult.put("followings", followingList); // "user" : profileDto
        followResult.put("followers", followerList);
        result.put("data", followResult);
            /*
            {
              "code": "200",
              "data": {
                "follows": [
                  {
                    "followSeq": 0,
                    "senderSeq": 0,
                    "receiverSeq": 1,
                    "followCreated": ""
                  }
                ]
              }
            }
            */
        return ResponseEntity.ok().body(result);
    }
}
