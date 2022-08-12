package com.common.dipping.api.dipping.domain.dto;

import com.common.dipping.api.dipping.domain.entity.Dipping;
import lombok.Data;

@Data
public class DippingDto {
	private String dippingTitle;
	private String dippingContent;
	private Long parentId;
	private Boolean openDipping;
	private Long userId;
}
