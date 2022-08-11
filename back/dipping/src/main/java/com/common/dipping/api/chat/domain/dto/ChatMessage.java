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

    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private String time;


    @Builder
    public ChatMessage(String sender, String message, String time) {
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

}
