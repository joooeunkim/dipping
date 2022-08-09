package com.common.dipping.api.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {

    private String email;
    private String nickname;
    private String profileImgUrl;
    private String musicTaste;
    private String provider;
    private String musicGenre;
    private String createAt;
    private String updateAt;
    private Boolean openUser;
    private int boardCount;
    private Long followerCount;
    private Long followingCount;
    private Boolean isMe;


}
