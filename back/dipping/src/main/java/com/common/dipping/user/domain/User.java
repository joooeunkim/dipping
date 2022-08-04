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
public class User extends Common  implements Serializable {

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Setter
    @Column(nullable = true)
    private String pw;

    @Setter
    @Column(nullable = false, unique = true)
    private String userNickname;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = true)
    private String profileImgUrl;

    @Column(nullable = true)
    private String userMusicTaste;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "Boolean default true")
    private Boolean openUser;

    @Column(nullable = true)
    private String provider;

    @Column(nullable = true)
    private String musicGerne;

    public void profileEdit(String userNickname, String profileImgUrl, String userMusicTaste, Boolean openUser, String musicGerne) {
        this.userNickname = userNickname;
        this.profileImgUrl = profileImgUrl;
        this.userMusicTaste = userMusicTaste;
        this.openUser = openUser;
        this.musicGerne = musicGerne;
        this.updatedAt = LocalDateTime.now();
    }

}