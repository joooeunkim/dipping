package com.common.dipping.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.board.domain.entity.Board;
import com.common.dipping.board.domain.entity.Like;
import com.common.dipping.user.domain.entity.User;

public interface LikeRepository extends JpaRepository<Like, Long>{

	Long countByBoardSeq(Board board);
	
	Like findByUserSeq(User user);

	boolean existsByUserSeqAndBoarSeq(User user, Board board);
}
