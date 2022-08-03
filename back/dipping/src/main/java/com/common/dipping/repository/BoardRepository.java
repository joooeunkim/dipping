package com.common.dipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.domain.entity.Board;
import com.common.dipping.user.domain.User;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	Board findAllByboardSeq(long boardSeq);

	long findTop1ByUserIdOrderByBoardSeqDesc(User user);
}
