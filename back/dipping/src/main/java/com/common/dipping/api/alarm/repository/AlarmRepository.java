package com.common.dipping.api.alarm.repository;

import com.common.dipping.api.alarm.domain.entity.Alarm;
import com.common.dipping.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByReceiver(User receiver);
}
