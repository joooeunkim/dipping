package com.common.dipping.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.board.domain.entity.Board;
import com.common.dipping.board.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	Long countByBoardSeq(Board board);
}
