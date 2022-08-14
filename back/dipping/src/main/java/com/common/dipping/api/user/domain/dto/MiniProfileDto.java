package com.common.dipping.api.user.domain.dto;

import com.common.dipping.api.user.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniProfileDto {
    private String nickname;
    private String profileImgUrl;

    public MiniProfileDto(User user) {
        this.nickname = user.getNickname();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}
