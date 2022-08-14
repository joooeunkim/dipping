package com.common.dipping.api.dipping.repository;

import com.common.dipping.api.dipping.domain.entity.Dipping;
import com.common.dipping.api.dipping.domain.entity.DippingSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DippingRepository extends JpaRepository<Dipping,Long> {
    @Query("select ds from DippingSong ds where ds.dipping.id = :dippingId ")
    List<DippingSong> findDippingByDippingId(@Param("dippingId")Long dippingId);

    List<Dipping> findAllByParentDipping(Dipping parentDipping);

    List<Dipping> findAllByDippingTitleContaining(String keyword);
}
