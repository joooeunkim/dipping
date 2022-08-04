package com.common.dipping.board.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.common.dipping.user.domain.entity.User;

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
	public Comment(String content, long parentSeq, String createdAt) {
		this.content = content;
		this.parentSeq = parentSeq;
		this.createdAt = createdAt;
	}
	
	@ManyToMany(mappedBy = "comments", fetch = FetchType.LAZY)
    private List<Like> Likes = new ArrayList<>();
	
	// 게시판 연결
	@ManyToOne
    @JoinColumn(name = "boardSeq")
    private Board board;
	// 댓글작성자번호 연결
	@ManyToOne
    @JoinColumn(name = "id")
    private User user;
}
