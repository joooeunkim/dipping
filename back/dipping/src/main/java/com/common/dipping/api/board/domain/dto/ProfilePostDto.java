package com.common.dipping.api.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfilePostDto {
    private Long boardId; //포스트 하나
    private String songImgUrl; //대표 이미지
}
