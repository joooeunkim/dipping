package com.common.dipping.api.user.domain.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class StorageId implements Serializable {
    private Long userId;
    private Long boardId;
}
