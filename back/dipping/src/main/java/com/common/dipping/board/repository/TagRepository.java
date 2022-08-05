package com.common.dipping.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.board.domain.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	Tag findByContent(String content);

}
