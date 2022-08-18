package com.common.dipping.api.dipping.repository;

import com.common.dipping.api.dipping.domain.entity.Dipping;
import com.common.dipping.api.dipping.domain.entity.DippingHeart;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DippingHeartRepository extends JpaRepository<DippingHeart,Long> {

    @Query("select count(h) from DippingHeart h where h.dipping.id = :dippingId ")
    int findHeartsCountByDippingId(@Param("dippingId") Long dippingId);

    boolean existsByUserAndDipping(User user, Dipping dipping);

    Optional<DippingHeart> findByUserAndDipping(User user, Dipping dipping);
    Optional<List<DippingHeart>> findAllByDipping(Dipping dipping);
}
