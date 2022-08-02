package com.common.dipping.user.domain;

import com.common.dipping.enums.UserRole;
import com.common.dipping.user.common.Common;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.DataInput;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "USER")
@Getter
@Builder
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Common implements Serializable {

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Setter
    @Column(nullable = false)
    private String pw;

    @Setter
    @Column(nullable = false, unique = true)
    private String userNickname;

    @Setter
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Setter
    @Column(nullable = true)
    private String profileImgUrl;

    @Setter
    @Column(nullable = true)
    private String userMusicTaste;

    @Setter
    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Setter
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Setter
    @Column(columnDefinition = "Boolean default true")
    private Boolean openUser;

    @Column(nullable = true)
    private String provider;

    @Setter
    @Column(nullable = true)
    private String musicGerne;

}