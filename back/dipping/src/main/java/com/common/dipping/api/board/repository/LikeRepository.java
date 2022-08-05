package com.common.dipping.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Like;
import com.common.dipping.api.user.domain.entity.User;

public interface LikeRepository extends JpaRepository<Like, Long>{

	Long countByBoardSeq(Board board);
	
	Like findByUserSeq(User user);

	boolean existsByUserSeqAndBoarSeq(User user, Board board);
}
