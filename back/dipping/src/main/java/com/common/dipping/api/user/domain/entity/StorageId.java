package com.common.dipping.api.user.domain.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StorageId implements Serializable {
    private Long userId;
    private Long boardId;
}
