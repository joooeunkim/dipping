package com.common.dipping.api.dipping.repository;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.dipping.domain.entity.Dipping;
import com.common.dipping.api.dipping.domain.entity.DippingSong;
import com.common.dipping.api.user.domain.entity.User;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DippingRepository extends JpaRepository<Dipping,Long> {
    @Query("select ds from DippingSong ds where ds.dipping.id = :dippingId ")
    List<DippingSong> findDippingByDippingId(@Param("dippingId")Long dippingId);

    List<Dipping> findAllByParentDipping(Dipping parentDipping);

    List<Dipping> findAllByDippingTitleContaining(String keyword);

    @Query(nativeQuery = true,value = "select d.* from dipping as d join dipping_heart as dh on d.id = dh.dipping_id where dh.created_at >= :createdAt AND d.parent_dipping is null group by dh.dipping_id order by count(dh.id) DESC ")
    List<Dipping> findAllWithDippingHeartByCreatedAt(@Param("createdAt") LocalDateTime createdAt);

    @Query("select count(d) from Dipping d where d.parentDipping.id = :dippingId ")
    int findChildCountByDippingId(@Param("dippingId") Long dippingId);

    List<Dipping> findAllByParentDippingNullAndUserNot(Sort id, User user);

    @Query("select d from Dipping d where d.user.id = :userId")
    List<Dipping> findAllWithUserId(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select d.* from dipping as d join (select receiver from follow where sender = :userId) f " +
            "on d.user_id = f.receiver where d.parent_dipping is null order by created_at DESC")
    List<Dipping> findAllWithFollowingUser(@Param("userId") Long userId);
}
