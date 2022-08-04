package com.common.dipping.api.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {

    private String email;
    private String userNickname;
    private String profileImgUrl;
    private String userMusicTaste;
    private String provider;
    private String musicGerne;
    private String createAt;
    private String updateAt;
    private Boolean openUser;


}
