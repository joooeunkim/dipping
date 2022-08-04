package com.common.dipping.api.board.domain.dto;

import lombok.Data;

@Data
public class PostTagDto {
	
	private long tagSeq;
	private long boardSeq;
	private String content;
}
