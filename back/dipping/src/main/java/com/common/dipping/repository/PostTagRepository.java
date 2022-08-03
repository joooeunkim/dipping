package com.common.dipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.domain.entity.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Integer>{

}
