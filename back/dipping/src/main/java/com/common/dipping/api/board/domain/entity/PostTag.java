package com.common.dipping.api.board.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.common.dipping.common.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
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


}
