package com.common.dipping.api.board.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.common.dipping.common.Common;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostTag extends Common {

	
	// 태그 연결
	@ManyToOne
    @JoinColumn(name = "tagId")
    private Tag tag;

	// 게시판 연결
	@ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

	@Builder
	public PostTag(Tag tag, Board board) {
		this.tag = tag;
		this.board = board;
	}
	
	
	
	
}
