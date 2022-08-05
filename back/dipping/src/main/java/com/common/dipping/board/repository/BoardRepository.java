package com.common.dipping.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.board.domain.entity.Board;
import com.common.dipping.user.domain.entity.User;

public interface BoardRepository extends JpaRepository<Board, Long>{

	Board findByboardSeq(Long boardSeq);

	List<Board> findAllByUserSeq(User toUser);

}
