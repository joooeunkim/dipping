package com.common.dipping.user.dto;

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
