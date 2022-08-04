package com.common.dipping.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

	Board findByboardSeq(Long boardSeq);

}
