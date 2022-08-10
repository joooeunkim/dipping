package com.common.dipping.api.chat.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * REDIS 저장 채팅 메시지
 * key: roomID
 */
@Data
public class ChatMessage implements Serializable {

    //MismatchedInputException 오류 관련(https://jinseongsoft.tistory.com/232)
    public ChatMessage() {
    }

    // 메시지 타입 : 채팅방 입장, 채팅방 메시지보내기, 채팅방 나가기
    public enum MessageType {
        ENTER, TALK, QUIT, SYNC, KICK, KICK_DUP
    }
    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private long userCount;  // 채팅방 인원수, 채팅방 내에서 메시지가 전달될때 인원수 갱신시 사용
    private String profileImgUrl; // 유저 이미지(ENTER 시점에 캐싱)
    private long userId; // 유저 아이디

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String receiver, String message, long userCount, String profileImgUrl, long userId) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.userCount = userCount;
        this.profileImgUrl = profileImgUrl;
        this.userId = userId;
    }

}
