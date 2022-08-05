package com.common.dipping.api.user.domain.entity;

import com.common.dipping.common.Common;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Follow")
public class Follow extends Common {

    @JoinColumn(name = "sender")
    @ManyToOne
    private User sender;

    @JoinColumn(name = "receiver")
    @ManyToOne
    private User receiver;

    @Builder
    public Follow(User fromUser, User toUser) {
        this.sender = fromUser;
        this.receiver = toUser;
    }
}
