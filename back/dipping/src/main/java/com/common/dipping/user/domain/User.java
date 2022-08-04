package com.common.dipping.user.domain;

import com.common.dipping.enums.UserRole;
import com.common.dipping.user.common.Common;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Common  implements Serializable {

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Setter
    @Column(nullable = true)
    private String pw;

    @Setter
    @Column(nullable = true, unique = true)
    private String userNickname;

    @Setter
    @Column(nullable = true, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    @Column(nullable = true, length = 20, updatable = false)
    private LocalDateTime createdAt;                        // 등록 일자

    @UpdateTimestamp
    @Column(length = 20)
    private LocalDateTime updatedAt;                        // 수정 일자

    @Column(nullable = true)
    private String provider;

}