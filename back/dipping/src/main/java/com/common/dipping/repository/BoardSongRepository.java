package com.common.dipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.domain.entity.BoardSong;

public interface BoardSongRepository extends JpaRepository<BoardSong, Long>{

}
