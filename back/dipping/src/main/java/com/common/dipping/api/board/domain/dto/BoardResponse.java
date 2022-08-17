package com.common.dipping.api.board.domain.dto;

import com.common.dipping.api.board.domain.entity.Board;

import lombok.Data;

@Data
public class BoardResponse {

	private Long id;
	private Long boardId;
	private Long userId;
	private String nickname;
	private String content;
	private Boolean openPost;
	private Boolean openComment;
	private Boolean albumArt;
	private int likeCount;
	private Boolean myLike;
	private int commentCount;
	private String createdAt;
	private String updatedAt;
	
	public BoardResponse(Board board) {
		this.boardId = board.getId();
		this.userId = board.getUser().getId();
		this.content = board.getContent();
		this.openPost = board.isOpenPost();
		this.openComment = board.isOpenComment();
		this.albumArt = board.isAlbumArt();
		this.nickname = board.getUser().getNickname();
		this.createdAt = board.getCreatedAt().toString();
		this.updatedAt = board.getUpdatedAt().toString();
	}
	
}
