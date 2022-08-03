package com.common.dipping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.domain.entity.UserTag;

public interface UserTagRepository extends JpaRepository<UserTag, Integer>{

}
