package com.common.dipping.api.board.domain.dto;

import com.common.dipping.api.board.domain.entity.Board;
import lombok.Data;

import java.time.format.DateTimeFormatter;

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
