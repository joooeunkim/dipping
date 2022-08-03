package com.common.dipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.domain.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Integer>{

}
