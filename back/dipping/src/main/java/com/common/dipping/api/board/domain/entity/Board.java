package com.common.dipping.api.board.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

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

	// 사용자 번호 연결해야 한다.
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private List<Comment> comments = new ArrayList<>();
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Heart> hearts = new ArrayList<>();

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private List<BoardSong> boardSongs = new ArrayList<>();

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private List<PostTag> postTags = new ArrayList<>();

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private List<UserTag> userTags = new ArrayList<>();
}
