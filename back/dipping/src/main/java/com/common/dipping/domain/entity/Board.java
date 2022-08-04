package com.common.dipping.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.common.dipping.user.domain.User;

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
	@Column(nullable = true)
	private Date createAt;
	@Column(nullable = true)
	private String updateAt;
	private boolean openPost;
	private boolean openComment;
	private boolean albumart;

	@Builder
	public Board(String content, String createAt, boolean openPost, boolean openComment, boolean albumart,
			User user) {
		this.content = content;
		this.createAt = new Date();
		this.openPost = openPost;
		this.openComment = openComment;
		this.albumart = albumart;
		this.user = user;
	}

	// 사용자 번호 연결해야 한다.
	@ManyToOne
	@JoinColumn(name = "userSeq")
	private User user;

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Like> Likes = new ArrayList<>();

}
