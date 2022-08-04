package com.common.dipping.board.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostTag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long postTagSeq;
	
	// 태그 연결
	// 게시판 연결
	@ManyToOne
    @JoinColumn(name = "tagSeq")
    private Tag tag;
	
	@ManyToOne
    @JoinColumn(name = "boardSeq")
    private Board board;

	@Builder
	public PostTag(Tag tag, Board board) {
		this.tag = tag;
		this.board = board;
	}
	
	
	
	
}
