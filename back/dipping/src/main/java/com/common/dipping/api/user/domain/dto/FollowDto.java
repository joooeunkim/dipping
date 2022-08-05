package com.common.dipping.api.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDto {

    private String receiverNickname;
    private String senderNickname;

}
