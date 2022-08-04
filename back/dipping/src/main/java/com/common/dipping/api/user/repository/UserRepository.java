package com.common.dipping.api.user.repository;

import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //User findByEmailAndPassword(String email, String password);
	
	Optional<User> findById(Long id);


    Optional<User> findByUserNickname(String userNickname);

    Optional<User> findByEmail(String email);

    Optional<User> findAllByUserNickname(String userNickname);

    boolean existsByEmail(String email);

    boolean existsByUserNickname(String userNickname);

}
