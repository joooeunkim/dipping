package com.common.dipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.domain.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

	Tag findByContent(String content);

}
