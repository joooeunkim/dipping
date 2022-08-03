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
public class BoardSong {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long songSeq;
	private String songTitle;
	private String songSinger;
	private String songUrl;
	private String songImgUrl;
	
	@Builder
	public BoardSong(String songTitle, String songSinger, String songUrl, String songImgUrl) {
		super();
		this.songTitle = songTitle;
		this.songSinger = songSinger;
		this.songUrl = songUrl;
		this.songImgUrl = songImgUrl;
	}
	

	// 게시판번호 연결
	// 사용자번호 연결 안해도 될듯??
}
