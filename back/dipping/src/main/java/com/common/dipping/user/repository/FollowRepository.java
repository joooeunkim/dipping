package com.common.dipping.user.repository;

import com.common.dipping.user.domain.Follow;
import com.common.dipping.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByFromUserAndToUser(User fromUser, User toUser);

    void deleteById(Long id);

    Follow save(Follow follow);

}
