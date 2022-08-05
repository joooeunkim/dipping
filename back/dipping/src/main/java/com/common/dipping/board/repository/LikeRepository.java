package com.common.dipping.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.board.domain.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long>{

}
