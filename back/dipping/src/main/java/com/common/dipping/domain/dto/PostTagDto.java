package com.common.dipping.domain.dto;

import lombok.Data;

@Data
public class PostTagDto {
	
	private long tagSeq;
	private long boardSeq;
	private String content;
}
