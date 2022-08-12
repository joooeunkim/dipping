package com.common.dipping.api.dipping.repository;

import com.common.dipping.api.dipping.domain.entity.Dipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DippingRepository extends JpaRepository<Dipping,Long> {
}
