package com.common.dipping.api.board.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.common.dipping.api.user.domain.entity.User;

import com.common.dipping.common.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTag extends Common {

	@ManyToOne
    @JoinColumn(name = "userId")
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;


}
