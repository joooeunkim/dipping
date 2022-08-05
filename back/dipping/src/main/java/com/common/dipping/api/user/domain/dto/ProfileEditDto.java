package com.common.dipping.api.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileEditDto {

    private String email;
    private String nickname;
    private String profileImgUrl;
    private String musicTaste;
    private Boolean openUser;
    private String musicGenre;

}
