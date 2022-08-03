package com.common.dipping.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long boardSeq;
	private String content;
	private String createAt;
	private String updateAt;
	private boolean openPost;
	private boolean openComment;
	private boolean isAlbumart;
	
	@Builder
	public Board(String content, String createAt, boolean openPost, boolean openComment,
			boolean isAlbumart) {
		super();
		this.content = content;
		this.createAt = createAt;
		this.openPost = openPost;
		this.openComment = openComment;
		this.isAlbumart = isAlbumart;
	}
	
}
