package com.common.dipping.api.board.domain.dto;

import lombok.Data;

@Data
public class LikeDto {

	private Long likeId;
	private String createdAt;
	private Long commentId;
	private Long boardId;
}
