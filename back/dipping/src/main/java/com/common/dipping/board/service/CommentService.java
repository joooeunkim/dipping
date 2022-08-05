package com.common.dipping.board.service;

import org.springframework.stereotype.Service;

import com.common.dipping.board.domain.entity.Board;
import com.common.dipping.board.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	public long getCountByBoardSeq(Board board) {
		return commentRepository.countByBoardSeq(board);
	}
}
