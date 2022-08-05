package com.common.dipping.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.board.domain.entity.Board;
import com.common.dipping.board.domain.entity.BoardSong;

public interface BoardSongRepository extends JpaRepository<BoardSong, Long>{

	List<BoardSong> findAllByBoardSeq(Board board);

}
