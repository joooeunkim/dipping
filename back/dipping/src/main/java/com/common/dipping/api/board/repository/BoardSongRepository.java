package com.common.dipping.api.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.BoardSong;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardSongRepository extends JpaRepository<BoardSong, Long>{

	List<BoardSong> findAllByBoardId(Board board);

	@Query("select bs from BoardSong bs where bs.board.id = :boardId ")
	List<BoardSong> findBoardSongByBoardId(@Param("boardId") Long boardId);

	void deleteAllByBoard(Board board);
}
