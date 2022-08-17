package com.common.dipping.api.board.repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long>{

	Optional<Board> findById(Long id);

    @Query("select b from Board b where b.user.id = :userId and b.createdAt >= :createdAt and b.openPost = true")
    List<Board> findAllWithUserIdAndCreatedAtAfter(@Param("userId") Long userId, @Param("createdAt") LocalDateTime createdAt);

    @Query("select b from Board b where b.user.id = :userId and b.openPost = true")
    List<Board> findAllWithUserId(@Param("userId") Long userId);

    @Query("select b from Board as b where b.openPost = true and b.id in (:boards)")
    List<Board> findListById(@Param("boards") List<Long> boards);
}
