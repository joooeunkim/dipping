package com.common.dipping.api.board.repository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.InterestTag;
import com.common.dipping.api.board.domain.entity.PostTag;
import com.common.dipping.api.board.domain.entity.Tag;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestTagRepository extends JpaRepository<InterestTag, Long> {

    void deleteAllByUser(User user);

    List<InterestTag> findAllByTag(Tag tag);
}
