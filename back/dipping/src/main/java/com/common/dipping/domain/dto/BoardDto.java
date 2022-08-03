package com.common.dipping.domain.dto;

import lombok.Data;

@Data
public class BoardDto {

	private long boardSeq;
	private String content;
	private String createAt;
	private String updateAt;
	private boolean openPost;
	private boolean openComment;
	private boolean isAlbumart;
	private long userSeq;
}
