package com.common.dipping.api.user.domain.entity;

import com.common.dipping.common.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
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

}
