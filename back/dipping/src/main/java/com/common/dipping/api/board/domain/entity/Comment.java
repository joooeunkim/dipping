package com.common.dipping.api.board.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
public class Comment extends Common {

	private String content;

	@Column(nullable = true, columnDefinition = "bigint default 0")
	private Long parentId;

	public void Update(String content){
		this.content = content;
	}
	
	@OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Heart> Hearts = new ArrayList<>();
	
	// 게시판 연결
	@ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

	// 댓글작성자번호 연결
	@ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
