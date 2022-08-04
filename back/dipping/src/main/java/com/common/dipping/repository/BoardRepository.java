package com.common.dipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.domain.entity.Board;
import com.common.dipping.user.domain.User;

public interface BoardRepository extends JpaRepository<Board, Long>{

	Board findByboardSeq(Long boardSeq);

}
