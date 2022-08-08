package com.common.dipping.api.board.repository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Heart;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Long countByBoardId(Board board);

    Optional<Heart> findByUserId(User user);

    boolean existsByUserIdAndBoardId(User user, Board board);

    Optional<Heart> findByUserIdAndBoardId(User user, Board board);

    List<Heart> findAllByBoardId(Board board);
}
