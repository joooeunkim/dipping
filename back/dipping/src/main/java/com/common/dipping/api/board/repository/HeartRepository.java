package com.common.dipping.api.board.repository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Comment;
import com.common.dipping.api.board.domain.entity.Heart;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    @Query("select count(h) from Heart h where h.board.id = :boardId ")
    int findHeartsCountByBoardId(@Param("boardId") Long boardId);

    @Query("select count(h) from Heart h where h.comment.id = :commentId ")
    int findHeartsCountByCommentId(@Param("commentId") Long commentId);

    boolean existsByUserIdAndBoardId(Long user_id, Long board_id);

    Optional<Heart> findByUserIdAndBoardId(User user, Board board);

    List<Heart> findAllByBoardId(Board board);

    boolean existsByUserIdAndCommentId(User user, Comment comment);

    Optional<Heart> findByUserIdAndCommentId(User user, Comment comment);
}
