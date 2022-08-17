package com.common.dipping.api.board.domain.dto;

import lombok.Data;

@Data
public class PostTagDto {

	private Long Id;
	private Long tagId;
	private Long boardId;
	private String content;
}
