package com.common.dipping.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileEditDto {

    private String email;
    private String userNickname;
    private String profileImgUrl;
    private String userMusicTaste;
    private Boolean openUser;

}
