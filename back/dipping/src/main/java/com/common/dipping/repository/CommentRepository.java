package com.common.dipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
