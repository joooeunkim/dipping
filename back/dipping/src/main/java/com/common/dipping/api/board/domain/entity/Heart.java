package com.common.dipping.api.board.domain.entity;

import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.common.Common;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class Heart extends Common {

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardId", nullable = true)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "commentId", nullable = true)
    private Comment comment;

    @Builder
    public Heart(User user, Board board, Comment comment) {
        this.user = user;
        this.board = board;
        this.comment = comment;
    }
}
