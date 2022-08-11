package com.common.dipping.api.search.repository;

import com.common.dipping.api.search.domain.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

}
