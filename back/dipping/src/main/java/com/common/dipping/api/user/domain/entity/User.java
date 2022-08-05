package com.common.dipping.api.user.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Comment;
import com.common.dipping.api.board.domain.entity.UserTag;
import com.common.dipping.common.UserRole;
import com.common.dipping.common.Common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER")
@Getter
@AllArgsConstructor
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Common {

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Setter
    @Column(nullable = true)
    private String pw;

    @Setter
    @Column(nullable = true, unique = true)
    private String nickname;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String email, String pw, String nickname, UserRole role, String profileImgUrl, String musicTaste, Boolean openUser, String provider, String musicGenre) {
        this.email = email;
        this.pw = pw;
        this.nickname = nickname;
        this.role = role;
        this.profileImgUrl = profileImgUrl;
        this.musicTaste = musicTaste;
        this.openUser = openUser;
        this.provider = provider;
        this.musicGenre = musicGenre;
    }


	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

//    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
//    private List<Like> likes = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserTag userTag;

    @Column(nullable = true)
    private String profileImgUrl;

    @Column(nullable = true)
    private String musicTaste;

    @Column(columnDefinition = "Boolean default true")
    private Boolean openUser;

    @Column(nullable = true)
    private String provider;

    @Column(nullable = true)
    private String musicGenre;

    public void profileEdit(String userNickname, String profileImgUrl, String musicTaste, Boolean openUser, String musicGenre) {
        this.nickname = userNickname;
        this.profileImgUrl = profileImgUrl;
        this.musicTaste = musicTaste;
        this.openUser = openUser;
        this.musicGenre = musicGenre;
    }

}