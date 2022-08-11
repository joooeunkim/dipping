package com.common.dipping.api.chat.controller;

import com.common.dipping.api.chat.domain.dto.ChatMessage;
import com.common.dipping.api.chat.repository.ChatRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatsController {

    private final JwtProvider jwtProvider;

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    private final ChatRepository chatRoomRepository;

    // "/pub/chat/message"로 들어오는 메시징을 처리
    @MessageMapping("/chat/message")  // websocket으로 들어오는 메시지 발행(/sub/chat/room/{roomId}로 메시지를 send)
    public void message(ChatMessage message, @Header("Authorization") String token) {

        User user = jwtProvider.getUser(token);
        String nickname = user.getNickname();


        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        message.setProfileImgUrl(user.getProfileImgUrl());
        message.setUserId(user.getId());
        // Websocket에 발행된 메시지를 redis로 발행한다(publish). redisTemplate을 통해 바로 ChannelTopic으로 메시지를 발행
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
        // 발행한 메시지 저장
        chatRoomRepository.saveMessage(message.getRoomId(), message);
    }
}