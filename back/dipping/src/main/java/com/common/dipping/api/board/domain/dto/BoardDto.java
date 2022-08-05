package com.common.dipping.api.board.domain.dto;

import lombok.Data;

@Data
public class BoardDto {

	private Long id;
	private String content;
	private String createdAt;
	private String updatedAt;
	private boolean openPost;
	private boolean openComment;
	private boolean albumArt;
	private Long userId;
}
