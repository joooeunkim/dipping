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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	private final HeartService heartService;

	public int getCountByBoardId(Board board) {
		return commentRepository.findCommentsCount(board.getId());
	}

    public Long registerComment(CommentDto commentDto) {

		User user = userRepository.findById(commentDto.getUserId()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id="+commentDto.getUserId()));
		Board board = boardRepository.findById(commentDto.getBoardId()).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+commentDto.getBoardId()));
		Comment comment;
		comment = Comment.builder()
				.content(commentDto.getContent())
				.parentId(commentDto.getParentId())
				.board(board)
				.user(user)
				.build();

		return commentRepository.save(comment).getId();
    }

	public List<CommentDto> getlistCommentByboardId(Long userId,Long boardId){
		List<Comment> comments = commentRepository.findlistByBoardId(boardId).orElse(new ArrayList<>());
		List<CommentDto> commentDtos = new ArrayList<>();
		if(comments.isEmpty()){
			return commentDtos;
		}

		for (Comment c: comments) {
			CommentDto commentDto = new CommentDto();

			commentDto.setCommentId(c.getId());
			commentDto.setBoardId(c.getBoard().getId());
			commentDto.setUserId(c.getUser().getId());
			commentDto.setContent(c.getContent());
			commentDto.setParentId(c.getParentId());
			commentDto.setCreatedAt(c.getCreatedAt().toString());
			commentDto.setUpdatedAt(c.getUpdatedAt().toString());
			commentDto.setLikeCount(heartService.getCountByCommentId(c));
			commentDto.setMyLike(heartService.isMylikeByCommentId(userId,c));
			commentDto.setNickname(c.getUser().getNickname());
			commentDto.setProfileImgUrl(c.getUser().getProfileImgUrl());

			commentDtos.add(commentDto);
		}
		return commentDtos;
	}

	public boolean deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
		commentRepository.deleteByParentId(commentId);
		return commentRepository.existsById(commentId);
	}
	
	public void editComment(CommentDto commentDto) {
		Comment comment = commentRepository.findById(commentDto.getCommentId()).orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다. id="+commentDto.getCommentId()));
		comment.Update(commentDto.getContent());

		commentRepository.save(comment);
	}

	public Comment findById (Long parentId){
		Comment comment = commentRepository.findById(parentId).orElse(null);
		return comment;
	}

}
