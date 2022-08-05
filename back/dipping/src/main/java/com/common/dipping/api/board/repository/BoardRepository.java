package com.common.dipping.api.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.user.domain.entity.User;

public interface BoardRepository extends JpaRepository<Board, Long>{

	Optional<Board> findById(Long boardId);

}
