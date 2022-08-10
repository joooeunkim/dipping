package com.common.dipping.api.board.repository;

import com.common.dipping.api.board.domain.entity.Board;
<<<<<<< back/dipping/src/main/java/com/common/dipping/api/board/repository/PostTagRepository.java
=======
import com.common.dipping.api.board.domain.entity.Tag;
>>>>>>> back/dipping/src/main/java/com/common/dipping/api/board/repository/PostTagRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

import com.common.dipping.api.board.domain.entity.PostTag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long>{

    void deleteAllByBoard(Board board);
    
    List<PostTag> findAllByTag(Tag tag);

}
