package com.common.dipping.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	Tag findByContent(String content);

}
