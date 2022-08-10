package com.common.dipping.api.chat.controller;

import com.common.dipping.api.chat.domain.dto.ChatMessage;
import com.common.dipping.api.chat.repository.ChatRoomRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import com.common.dipping.jwt.JwtProvider;
import com.common.dipping.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 메시지 전송
    @PostMapping("/chat/message")
    public void message(@RequestBody ChatMessage message) {
        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        message.setImg(user.getProfileImgUrl());
        message.setUserId(user.getId());

        //메시지 저장
        chatRoomRepository.saveMessage(message.getRoomId(), message);
    }
}