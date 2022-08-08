package com.common.dipping.api.board.domain.dto;

import lombok.Data;

@Data
public class HeartDto {

	private Long likeId;
	private String createdAt;
	private Long commentId;
	private Long boardId;
}
