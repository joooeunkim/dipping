package com.common.dipping.api.alarm.domain.entity;

import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.common.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alarm extends Common {

    private String alarmMessage;

    private Boolean alarmRead;

    private String alarmType;

    @JoinColumn(name = "senderId")
    @ManyToOne
    private User sender;

    @JoinColumn(name = "receiverId")
    @ManyToOne
    private User receiver;

    public void read() {
        this.alarmRead = true;
    }
}
