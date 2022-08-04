package com.common.dipping.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.board.domain.entity.BoardSong;

public interface BoardSongRepository extends JpaRepository<BoardSong, Long>{

}
