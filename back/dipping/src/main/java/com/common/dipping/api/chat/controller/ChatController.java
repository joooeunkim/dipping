package com.common.dipping.api.chat.controller;

import com.common.dipping.api.chat.domain.dto.ChatMessage;
import com.common.dipping.api.chat.domain.dto.ChatRoom;
import com.common.dipping.api.chat.repository.ChatRepository;
import com.common.dipping.api.chat.service.ChatService;
import com.common.dipping.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
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

    // 나의 전체 채팅방 목록 조회
    @GetMapping("/rooms")
    public ResponseEntity findAllRoomOfUser(@AuthenticationPrincipal UserDetailsImpl userInfo) {
        return ResponseEntity.ok().body(chatService.findAllRoomOfUser(userInfo.getUsername()));
    }

    //사용자가 새로 채팅을 시작할 수 있는 사람 목록 조회
    @GetMapping("/new")
    public ResponseEntity findAllNewUser(@AuthenticationPrincipal UserDetailsImpl userInfo){
        return ResponseEntity.ok().body(chatService.findAllNewUser(userInfo.getUsername()));
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ChatRoom createRoom(@RequestParam String email, @AuthenticationPrincipal UserDetailsImpl userInfo) {
        return chatService.createChatRoom(userInfo.getUsername()+","+email);//채팅방 이름은 두 유저의 이메일을 ,로 연결한 값
    }

    // 채팅방 파괴
    @DeleteMapping("/room/{roomId}")
    public ResponseEntity deleteRoom(@PathVariable String roomId) {
        chatRepository.deleteChatRoom(roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    // 특정 채팅방 들어갔을때 채팅방 관련 정보를 전달
    @GetMapping("/room/{roomId}")
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRepository.findRoomById(roomId);
    }

    // 해당 채팅방에 저장된 최신 메시지 받기
    @GetMapping("/room/message/{roomId}")
    @ResponseBody
    public List<ChatMessage> getMessages(@PathVariable String roomId) {
        return chatRepository.getMessages(roomId);
    }

}
