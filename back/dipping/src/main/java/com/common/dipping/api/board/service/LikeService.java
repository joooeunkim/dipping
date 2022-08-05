package com.common.dipping.api.board.service;

import org.springframework.stereotype.Service;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.repository.LikeRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final UserRepository userRepository;

	public boolean isMylike(long userSeq, Board board) {
		User user = userRepository.findById(userSeq).orElse(null);
		
		boolean mylike = likeRepository.existsByUserSeqAndBoarSeq(user,board);
		return mylike;
	}

	public long getCountByBoardSeq(Board board) {
		return likeRepository.countByBoardSeq(board);
	}
}
