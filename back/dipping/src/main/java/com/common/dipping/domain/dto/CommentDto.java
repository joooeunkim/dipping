package com.common.dipping.domain.dto;

import lombok.Data;

@Data
public class CommentDto {

	private long commentSeq;	
	private String content;
	private long parentSeq;
	private String createdAt;
	private String updateAt;
	private long userSeq;
	private long boardSeq;
}
