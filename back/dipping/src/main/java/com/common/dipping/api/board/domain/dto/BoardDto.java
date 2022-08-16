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

	public BoardDto(Board board) {
		this.id = board.getId();
		this.content = board.getContent();
		this.createdAt = board.getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss"));
		this.updatedAt = board.getUpdatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss"));
		this.openPost = board.isOpenPost();
		this.openComment = board.isOpenComment();
		this.albumArt = board.isAlbumArt();
		this.userId = board.getUser().getId();
	}
}
