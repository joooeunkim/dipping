package com.common.dipping.service;

import org.springframework.stereotype.Service;

import com.common.dipping.domain.entity.Board;
import com.common.dipping.repository.BoardRepository;

@Service
public class BoardService {

	BoardRepository boardRepository;
	
	public Board getboardOne(long boardSeq) {
		Board board = boardRepository.findAllByboardSeq(boardSeq);
		return board;
	}
}
