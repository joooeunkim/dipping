package com.common.dipping.api.board.domain.dto;

import com.common.dipping.api.board.domain.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfilePostDto {
    private Long id; //포스트 하나
    private String songImgUrl; //대표 이미지

    public ProfilePostDto(Board board) {
        this.id = board.getId();
        this.songImgUrl = board.getBoardSongs().get(0).getSongImgUrl();
    }
}
