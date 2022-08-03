package com.common.dipping.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Like {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long likeSeq;
	private String createAt;
	
	// 좋아요한 사용자번호 연결
	// 댓글인지 연결
	// 게시판번호인지 연결
}
