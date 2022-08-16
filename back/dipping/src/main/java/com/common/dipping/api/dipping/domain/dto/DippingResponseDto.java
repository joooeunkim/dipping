package com.common.dipping.api.dipping.domain.dto;

import com.common.dipping.api.dipping.domain.entity.Dipping;
import lombok.Data;

@Data
public class DippingResponseDto {
    private Long dippingId;
    private Long userId;
    private String nickname;
    private String dippingTitle;
    private String dippingContent;
    private Boolean openDipping;
    private Long parentDipping;
    private String createdAt;
    private String updatedAt;
    private boolean myLike;
    private int likeCount;
    private int childCount;

    public DippingResponseDto(Dipping dipping) {
        this.dippingId = dipping.getId();
        this.userId = dipping.getUser().getId();
        this.nickname = dipping.getUser().getNickname();
        this.dippingTitle = dipping.getDippingTitle();
        this.dippingContent = dipping.getDippingContent();
        this.openDipping = dipping.getOpenDipping();
        this.parentDipping = (dipping.getParentDipping() != null ) ? dipping.getParentDipping().getId(): 0L;
        this.createdAt = dipping.getCreatedAt().toString();
        this.updatedAt = dipping.getCreatedAt().toString();
    }

    public void LikeAndChild(boolean myLike, int likeCount, int childCount){
        this.myLike = myLike;
        this.likeCount = likeCount;
        this.childCount = childCount;
    }
}
