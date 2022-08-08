package com.common.dipping.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    @Query("select count(c) from Comment c where c.board.id = :boardId ")
    int findCommentsCount(@Param("boardId") Long boardId);

    List<Comment> findAllByBoardId(Board board);
}
