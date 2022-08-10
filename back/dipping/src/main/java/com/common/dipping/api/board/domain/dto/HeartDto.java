package com.common.dipping.api.board.domain.dto;

import lombok.Data;

@Data
public class HeartDto {

	private Long Id;
	private Long commentId;
	private Long boardId;
	private Long userId;
}
