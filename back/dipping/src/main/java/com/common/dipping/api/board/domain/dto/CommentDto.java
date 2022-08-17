package com.common.dipping.api.board.domain.dto;

import lombok.Data;

@Data
public class CommentDto {

	private Long commentId;
	private String content;
	private Long parentId;
	private String createdAt;
	private String updatedAt;
	private Long userId;
	private Long boardId;
	private Boolean myLike;
	private int likeCount;
	private String nickname;
}
