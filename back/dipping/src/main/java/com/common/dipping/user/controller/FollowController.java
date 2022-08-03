package com.common.dipping.user.controller;

import com.common.dipping.user.domain.Follow;
import com.common.dipping.user.dto.FollowDto;
import com.common.dipping.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public Follow followUser(@RequestBody FollowDto followDto) {

        return followService.save(followDto.getFromUser(), followDto.getToUser());
    }

    @DeleteMapping("/follow")
    public void unFollowUser(@RequestBody FollowDto followDto) {
        Long id = followService.getFollowIdByFromEmailToEmail(followDto.getFromUser(), followDto.getToUser());
        followService.unFollow(id);
    }
}
