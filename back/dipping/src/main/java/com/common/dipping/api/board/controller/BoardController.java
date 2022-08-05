package com.common.dipping.api.board.controller;

import java.util.*;

import com.common.dipping.api.board.domain.dto.*;
import com.common.dipping.api.board.domain.entity.Comment;
import com.common.dipping.security.UserDetailsImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.BoardSong;
import com.common.dipping.api.board.repository.BoardRepository;
import com.common.dipping.api.board.service.BoardService;
import com.common.dipping.api.board.service.CommentService;
import com.common.dipping.api.user.domain.entity.Follow;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import com.common.dipping.api.user.service.FollowService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final CommentService commentService;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final FollowService followService;

	@PostMapping
	public ResponseEntity<?> register(@RequestBody ObjectNode registerObj)
			throws JsonProcessingException, IllegalArgumentException {

		ObjectMapper mapper = new ObjectMapper();

		BoardDto boardDto = mapper.treeToValue(registerObj.get("post"), BoardDto.class);
		User user = userRepository.findById(boardDto.getUserId()).orElse(null);

		List<PostTagDto> postTagDto = Arrays
				.asList(mapper.treeToValue(registerObj.get("post_tag"), PostTagDto[].class));
		List<UserTagDto> userTagDto = Arrays
				.asList(mapper.treeToValue(registerObj.get("user_tag"), UserTagDto[].class));
		List<BoardSongDto> boardSongDto = Arrays
				.asList(mapper.treeToValue(registerObj.get("playlist"), BoardSongDto[].class));

		Long boardId = boardService.register(boardDto);
		boardService.registerSong(boardSongDto, boardId);
		boardService.registerTag(postTagDto, userTagDto, boardId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/board")
	public ResponseEntity<?> getBoardOne(@Param("boardSeq") long boardSeq) {

		Board board = boardService.getboardOne(boardSeq);
		Map<String, Object> result = new HashMap<String, Object>();
		if (board != null) {
			result.put("code", 200);
			Map<String, Object> post = new HashMap<String, Object>();
			post.put("item", board);
			result.put("data", post);
		} else if (board == null) {
			ResponseEntity.badRequest();
		}

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/user")
	public ResponseEntity<?> getfollowingBoard(@Param("userSeq") long userSeq, @Param("pageNum") int pageNum) {

		Map<String, Object> result = new HashMap<String, Object>();
		long number = 1;
		// 해당 유저의 팔로우 유저들을 찾아와서 포스트 검색하여 boardSeq 내림차순으로 정렬

		// 팔로우 중인 유저들 리스트 조회
		List<Follow> follows = followService.getfollowListByFromUser(userSeq);
		if (follows == null) {
			// 팔로우 중인 사람 없음 표시
			result.put("message", "fail");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		} else {
			result.put("code", 200);
			for (int i = 0; i < follows.size(); i++) {
				Map<String, Object> posts = new HashMap<String, Object>();

				List<Board> boards = new ArrayList<>();
				if (boards != null) {
					BoardResponse boardResponse = new BoardResponse(boards.get(i));
					boardResponse.setId(number++);
					//boardResponse.setLikeCount(likeService.getCountByBoardSeq(boards.get(i)));
					//boardResponse.setMyLike(likeService.isMylike(userSeq, boards.get(i)));
					boardResponse.setCommentCount(commentService.getCountById(boards.get(i)));

					posts.put("item", boardResponse);

					for (int j = 0; j < boards.size(); j++) {
						List<BoardSong> boardSongs = boardService.getBoardSongAllById(boards.get(i));

						if (boardSongs != null) {
							posts.put("music", boardSongs);
						}
					}
				}
				result.put("posts", posts);
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("/comment")
	public ResponseEntity<?> registerComment(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody CommentDto commentDto) {
		commentDto.setUserId(userInfo.getId());
		Long commentId = commentService.registerComment(commentDto);

		if(commentId != 0L){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/comment")
	public ResponseEntity<?> getCommentByBoardId(@Param("boardId") Long boardId){
		List<Comment> comments = commentService.getlistCommentByboardId(boardId);
		Map<String, Object> result = new HashMap<String, Object>();
		if(comments != null){
			result.put("code", 200);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("comment", comments);
			result.put("data",data);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}else {
			result.put("code",201);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}
}
