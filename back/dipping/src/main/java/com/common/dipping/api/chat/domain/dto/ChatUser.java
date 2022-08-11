package com.common.dipping.api.chat.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatUser implements Serializable {

    public ChatUser() {
    }

    private String roomId; // 방번호
    private String sender; // 유저 닉네임
    private String userId; // 유저 번호

    @Builder
    public ChatUser(String roomId, String sender, String userId) {
        this.roomId = roomId;
        this.sender = sender;
        this.userId = userId;
    }
}