package com.common.dipping.api.user.repository;

import com.common.dipping.api.user.domain.dto.MiniProfileDto;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findById(Long id);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    Optional<User> findAllByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmailAndProvider(String email, String provider);

    boolean existsByEmailAndProvider(String email, String dipping);

    List<User> findAllByNicknameContaining(String keyword);

    List<User> findAll();
}
