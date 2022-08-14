package com.common.dipping.api.chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ChatUserList {

    private String email;
    private String nickname;
    private String profileImgUrl;

}
