package com.common.dipping.user.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.common.dipping.domain.entity.Board;
import com.common.dipping.domain.entity.Comment;
import com.common.dipping.domain.entity.Like;
import com.common.dipping.domain.entity.UserTag;
import com.common.dipping.enums.UserRole;
import com.common.dipping.user.common.Common;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Common implements Serializable {

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Setter
    @Column(nullable = false)
    private String pw;

    @Setter
    @Column(nullable = false, unique = true)
    private String nickname;

    @Setter
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    @Builder
    public User(String email, String pw, String nickname, UserRole role) {
		this.email = email;
		this.pw = pw;
		this.nickname = nickname;
		this.role = role;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserTag userTag;
}