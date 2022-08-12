package com.common.dipping.api.dipping.repository;

import com.common.dipping.api.dipping.domain.entity.DippingSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DippingSongRepository extends JpaRepository<DippingSong,Long> {
}
