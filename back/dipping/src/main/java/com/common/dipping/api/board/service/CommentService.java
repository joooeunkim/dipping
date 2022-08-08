package com.common.dipping.api.board.service;

import com.common.dipping.api.board.domain.dto.CommentDto;
import com.common.dipping.api.board.domain.entity.Comment;
import com.common.dipping.api.board.repository.BoardRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;

	public long getCountById(Board board) {
		return commentRepository.countByBoardId(board);
	}

    public Long registerComment(CommentDto commentDto) {

		User user = userRepository.findById(commentDto.getUserId()).orElse(null);
		Board board = boardRepository.findById(commentDto.getBoardId()).orElse(null);
		Comment comment;
		if(commentDto.getParentId() == 0L){
			comment = Comment.builder()
					.content(commentDto.getContent())
					.board(board)
					.user(user)
					.build();
		}else {
			comment = Comment.builder()
					.content(commentDto.getContent())
					.parentId(commentDto.getParentId())
					.board(board)
					.user(user)
					.build();
		}

		return commentRepository.save(comment).getId();
    }

	public List<Comment> getlistCommentByboardId(Long boardId){
		Board board = boardRepository.findById(boardId).orElse(null);
		List<Comment> comments = commentRepository.findAllByBoardId(board);
		return comments;
	}
}
