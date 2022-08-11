package com.common.dipping.api.board.domain.dto;

import lombok.Data;

@Data
public class UserTagDto {

	private Long userTagId;
	private Long boardId;
	private Long senderId;
	private Long receiverId;
	private String content;
}
