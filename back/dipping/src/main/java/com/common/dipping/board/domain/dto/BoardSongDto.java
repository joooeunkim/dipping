package com.common.dipping.board.domain.dto;

import lombok.Data;

@Data
public class BoardSongDto {

	private long songSeq;
	private String songTitle;
	private String songSinger;
	private String songUrl;
	private String songImgUrl;
	private long boardSeq;
}
