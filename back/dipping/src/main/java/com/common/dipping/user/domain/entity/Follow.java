package com.common.dipping.user.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "sender")
    @ManyToOne
    private User sender;

    @JoinColumn(name = "receiver")
    @ManyToOne
    private User receiver;

    @Column(nullable = true)
    private LocalDateTime followCreated;

    @Builder
    public Follow(User fromUser, User toUser) {
        this.sender = fromUser;
        this.receiver = toUser;
        this.followCreated = LocalDateTime.now();
    }
}
