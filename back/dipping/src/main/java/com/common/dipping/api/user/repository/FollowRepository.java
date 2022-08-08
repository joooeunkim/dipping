package com.common.dipping.api.user.repository;

import com.common.dipping.api.user.domain.entity.Follow;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findBySenderAndReceiver(User sender, User receiver);

    void deleteById(Long id);

	List<Follow> findAllBySender(User sender);

	List<Follow> findAllByReceiver(User receiver);


}
