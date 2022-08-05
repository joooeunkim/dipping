package com.common.dipping.api.board.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.common.dipping.api.user.domain.entity.User;

import com.common.dipping.common.Common;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserTag extends Common {

	@OneToOne
    @JoinColumn(name = "userId")
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

	@Builder
	public UserTag( User user, Board board) {
		this.user = user;
		this.board = board;
	}
	
	
}
