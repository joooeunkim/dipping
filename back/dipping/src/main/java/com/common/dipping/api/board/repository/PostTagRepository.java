package com.common.dipping.api.board.repository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.InterestTag;
import com.common.dipping.api.board.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.PostTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long>{

    void deleteAllByBoard(Board board);
    
    List<PostTag> findAllByTag(Tag tag);

    @Query("select pt from PostTag as pt where pt.tag.id in (:tags) ")
    List<PostTag> findByTag(@Param("tags") List<Long> tags);
}
