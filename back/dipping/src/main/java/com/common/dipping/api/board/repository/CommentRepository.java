package com.common.dipping.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	Long countByBoardId(Board board);

    List<Comment> findAllByBoardId(Board board);
}
