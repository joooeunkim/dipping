package com.common.dipping.user.repository;

import com.common.dipping.user.domain.entity.Follow;
import com.common.dipping.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    void deleteById(Long id);

	List<Follow> findAllByFromUser(User user);


}
