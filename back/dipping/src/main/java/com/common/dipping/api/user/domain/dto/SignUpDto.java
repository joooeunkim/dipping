package com.common.dipping.api.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    private String email;
    private String password;
    private String nickname;
    private String profileImgUrl;
    private String musicTaste;
    private String provider;
    private String musicGenre;

}