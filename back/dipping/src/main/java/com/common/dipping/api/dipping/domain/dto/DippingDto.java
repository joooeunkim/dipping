package com.common.dipping.api.dipping.domain.dto;

import lombok.Data;

@Data
public class DippingDto {
    private Long id;
    private String dippingTitle;
    private String dippingContent;
    private Long parentId;
    private Boolean openDipping;
    private Long userId;
}

