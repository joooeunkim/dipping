package com.common.dipping.api.user.domain.entity;

import com.common.dipping.api.board.domain.entity.Board;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Storage {

    @EmbeddedId
    private StorageId storageId;

    @ManyToOne
    @JoinColumn(name = "boardId")
    @MapsId("boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    @MapsId("userId")
    private User user;
}
