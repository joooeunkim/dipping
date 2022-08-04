package com.common.dipping.user.dto;

import com.common.dipping.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDto {

    private String fromUser;
    private String toUser;

}
