package com.common.dipping.api.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowListDto {

    private Long followSeq;
    private Long senderSeq;
    private Long receiverSeq;
    private String followCreated;

}
