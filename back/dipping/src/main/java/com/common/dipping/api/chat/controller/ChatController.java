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

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    // "/pub/chat/message"로 들어오는 메시징을 처리
    @MessageMapping("/chat/message")  // websocket으로 들어오는 메시지 발행(/sub/chat/room/{roomId}로 메시지를 send)
    public void message(ChatMessage message, @AuthenticationPrincipal UserDetailsImpl userInfo) {
        String nickname = userInfo.getNickname();
        User user = userRepository.findAllByNickname(nickname).orElse(null);

        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        message.setImg(user.getProfileImgUrl());
        message.setUserId(user.getId());
        // Websocket에 발행된 메시지를 redis로 발행한다(publish). redisTemplate을 통해 바로 ChannelTopic으로 메시지를 발행
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
        // 발행한 메시지 저장
        chatRoomRepository.saveMessage(message.getRoomId(), message);
    }
}