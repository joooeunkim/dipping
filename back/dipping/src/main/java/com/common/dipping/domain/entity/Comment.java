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
public class Comment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentSeq;
	
	private String content;
	private long parentSeq;
	private String createdAt;
	private String updateAt;
	
	@Builder
	public Comment(String content, long parentSeq, String createdAt, String updateAt) {
		super();
		this.content = content;
		this.parentSeq = parentSeq;
		this.createdAt = createdAt;
	}
	
	
	// 게시판 연결
	// 댓글작성자번호 연결
}
