package com.common.dipping.api.chat.controller;

import com.common.dipping.api.chat.domain.dto.ChatMessage;
import com.common.dipping.api.chat.domain.dto.ChatRoom;
import com.common.dipping.api.chat.repository.ChatRepository;
import com.common.dipping.api.chat.service.ChatService;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatRepository chatRepository;
    private final ChatService chatService;



    @Operation(summary = "채팅방 목록", description = "나의 전체 채팅방 목록 조회")
    @GetMapping("/rooms")
    public ResponseEntity findAllRoomOfUser(@AuthenticationPrincipal UserDetailsImpl userInfo) {
        return ResponseEntity.ok().body(chatService.findAllRoomOfUser(userInfo.getUsername()));
    }

    @Operation(summary = "채팅할 수 있는 사람 목록", description = "사용자가 새로 채팅을 시작할 수 있는 사람 목록 조회")
    @GetMapping("/new")
    public ResponseEntity findAllNewUser(@AuthenticationPrincipal UserDetailsImpl userInfo){
        return ResponseEntity.ok().body(chatService.findAllNewUser(userInfo.getUsername()));
    }

    @Operation(summary = "채팅방 생성", description = "사용자와 상대방의 이메일로 채팅방 생성")
    @PostMapping("/room")
    public ChatRoom createRoom(@RequestParam String email, @AuthenticationPrincipal UserDetailsImpl userInfo) {
        return chatService.createChatRoom(userInfo.getUsername()+","+email);//채팅방 이름은 두 유저의 이메일을 ,로 연결한 값
    }

    @Operation(summary = "채팅방 파괴", description = "roomId로 조회되는 채팅방 파괴")
    @DeleteMapping("/room")
    public ResponseEntity deleteRoom(@RequestParam String roomId) {
        chatRepository.deleteChatRoom(roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(summary = "채팅방 정보", description = "roomId 채팅방의 채팅방 정보를 전달")
    @GetMapping("/room")
    public ChatRoom findRoomById(@RequestParam String roomId, @AuthenticationPrincipal UserDetailsImpl userInfo) {
        return chatService.findRoomById(roomId, userInfo.getUsername());
    }

    @Operation(summary = "채팅방에 메시지 저장", description = "roomId 채팅방에 채팅방 메시지를 저장")
    @PostMapping("/room/message")
    public ResponseEntity saveMessages(@RequestParam String roomId, @RequestBody ChatMessage message){
        return chatService.saveMessages(roomId, message);
    }

    @Operation(summary = "채팅방의 메시지 목록 반환", description = "roomId 채팅방에 저장된 모든 채팅방 메시지를 전달")
    @GetMapping("/room/message")
    @ResponseBody
    public List<ChatMessage> getMessages(@RequestParam String roomId) {
        return chatRepository.getMessages(roomId);
    }

}
