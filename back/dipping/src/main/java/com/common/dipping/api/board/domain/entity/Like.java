package com.common.dipping.api.board.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import com.common.dipping.api.user.domain.entity.User;

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
	@ManyToMany
    @JoinColumn(name = "id")
    private List<User> users = new ArrayList<>();
	// 댓글인지 연결
	@ManyToMany
    @JoinColumn(name = "commentSeq",nullable = true)
    private List<Comment> comments = new ArrayList<>();
	// 게시판번호인지 연결
	@ManyToMany
    @JoinColumn(name = "boardSeq",nullable = true)
    private List<Board> boards = new ArrayList<>();
}
