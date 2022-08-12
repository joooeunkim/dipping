package com.common.dipping.api.user.repository;

import com.common.dipping.api.board.domain.entity.Comment;
import com.common.dipping.api.user.domain.entity.Storage;
import com.common.dipping.api.user.domain.entity.StorageId;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, StorageId> {
    @Query("select s from Storage s where s.user.id = :userId")
    List<Storage> findlistByUserId(@Param("userId") Long userId);
}
