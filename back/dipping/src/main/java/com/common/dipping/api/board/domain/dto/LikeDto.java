package com.common.dipping.board.domain.dto;

import lombok.Data;

@Data
public class LikeDto {

	private long likeSeq;
	private String createAt;
	private long commentSeq;
	private long boardSeq;
}
