package com.common.dipping.api.user.domain.entity;

import com.common.dipping.common.Common;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "CODE")
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Code extends Common {

    @Column(nullable = false, unique = true)
    String code;

    @OneToOne
    @JoinColumn(name = "userId")
    User user;

}
