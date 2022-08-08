package com.common.dipping.api.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowingListDto {

    private Long followSeq;
    private Long receiverSeq;
    private String followCreated;
    private String profileImgUrl;
    private String nickname;

}
