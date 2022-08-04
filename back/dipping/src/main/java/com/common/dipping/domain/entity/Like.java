package com.common.dipping.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.common.dipping.user.domain.User;

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
	@ManyToOne
    @JoinColumn(name = "userSeq")
    private User user;
	// 댓글인지 연결
	@ManyToOne
    @JoinColumn(name = "commentSeq",nullable = true)
    private Comment comment;
	// 게시판번호인지 연결
	@ManyToOne
    @JoinColumn(name = "boardSeq",nullable = true)
    private Board board;
}
