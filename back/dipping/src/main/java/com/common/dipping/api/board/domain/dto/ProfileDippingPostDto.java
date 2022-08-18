package com.common.dipping.api.board.domain.dto;

import com.common.dipping.api.dipping.domain.entity.Dipping;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDippingPostDto {
    private Long id; //포스트 하나
    private String title; //제목
    private String songImgUrl; //대표 이미지

    public ProfileDippingPostDto(Dipping dipping) {
        this.id = dipping.getId();
        this.title = dipping.getDippingTitle();
        this.songImgUrl = dipping.getDippingSongs().get(0).getSongImgUrl();
    }
}
