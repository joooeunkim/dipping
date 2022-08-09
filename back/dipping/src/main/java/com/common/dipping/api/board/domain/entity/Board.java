package com.common.dipping.api.board.domain.entity;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.common.dipping.api.user.domain.entity.User;

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
public class Board extends Common {

	@Column(nullable = true)
	private String content;

	private boolean openPost;

	private boolean openComment;

	private boolean albumArt;

	@Builder
	public Board(String content, boolean openPost, boolean openComment, boolean albumArt,
			User user) {
		this.content = content;
		this.openPost = openPost;
		this.openComment = openComment;
		this.albumArt = albumArt;
		this.user = user;
	}

	public void update(String content, boolean openPost, boolean openComment, boolean albumArt){
		this.content = content;
		this.openPost = openPost;
		this.openComment = openComment;
		this.albumArt = albumArt;
	}

	// 사용자 번호 연결해야 한다.
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();
	
//	@ManyToMany(mappedBy = "boards", fetch = FetchType.LAZY)
//    private List<Like> Likes = new ArrayList<>();

}
