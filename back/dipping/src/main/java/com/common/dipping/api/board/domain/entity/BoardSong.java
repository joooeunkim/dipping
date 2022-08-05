package com.common.dipping.api.board.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.common.dipping.common.Common;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BoardSong extends Common {

	private String songTitle;
	private String songSinger;
	private String songUrl;
	private String songImgUrl;
	
	@Builder
	public BoardSong(String songTitle, String songSinger, String songUrl, String songImgUrl, Board board) {
		this.songTitle = songTitle;
		this.songSinger = songSinger;
		this.songUrl = songUrl;
		this.songImgUrl = songImgUrl;
		this.board = board;
	}
	

	// 게시판번호 연결
	@ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;
	// 사용자번호 연결 안해도 될듯??
}
