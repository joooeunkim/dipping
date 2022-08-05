package com.common.dipping.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Long>{

}
