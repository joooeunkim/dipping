package com.common.dipping.api.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

	Tag findByContent(String content);
	List<Tag> findAllByContentContaining(String Keyword);

}
