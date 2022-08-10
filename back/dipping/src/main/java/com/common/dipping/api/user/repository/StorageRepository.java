package com.common.dipping.api.user.repository;

import com.common.dipping.api.user.domain.entity.Storage;
import com.common.dipping.api.user.domain.entity.StorageId;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, StorageId> {
    Optional<List<Storage>> findByUser(User user);
}
