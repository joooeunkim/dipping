package com.common.dipping.api.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowerListDto {

    private Long followId;
    private Long senderId;
    private String followCreated;
    private String profileImgUrl;
    private String nickname;

}
