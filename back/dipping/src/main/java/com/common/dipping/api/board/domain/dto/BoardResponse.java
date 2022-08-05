package com.common.dipping.api.board.domain.dto;

import com.common.dipping.api.board.domain.entity.Board;

import lombok.Data;

@Data
public class BoardResponse {

	private long id;
	private long boardSeq;
	private long userSeq;
	private String content;
	private boolean openPost;
	private boolean openComment;
	private boolean Albumart;
	private long likeCount;
	private boolean myLike;
	private long commentcount;
	
	public BoardResponse(Board board) {
		this.boardSeq = board.getBoardSeq();
		this.userSeq = board.getUser().getId();
		this.content = board.getContent();
		this.openPost = board.isOpenPost();
		this.openComment = board.isOpenComment();
		Albumart = board.isAlbumart();
	}
	
}
