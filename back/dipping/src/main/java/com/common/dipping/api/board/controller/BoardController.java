package com.common.dipping.api.board.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.dipping.api.board.domain.dto.BoardDto;
import com.common.dipping.api.board.domain.dto.BoardSongDto;
import com.common.dipping.api.board.domain.dto.PostTagDto;
import com.common.dipping.api.board.domain.dto.UserTagDto;
import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.repository.BoardRepository;
import com.common.dipping.api.board.service.BoardService;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;

	@PostMapping
	public ResponseEntity<?> register(@RequestBody ObjectNode registerObj) throws JsonProcessingException, IllegalArgumentException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		BoardDto boardDto = mapper.treeToValue(registerObj.get("post"), BoardDto.class);
		User user = userRepository.findById(boardDto.getUserSeq()).orElse(null);
		
		List<PostTagDto> postTagDto = Arrays.asList(mapper.treeToValue(registerObj.get("post_tag"), PostTagDto[].class));
		List<UserTagDto> userTagDto = Arrays.asList(mapper.treeToValue(registerObj.get("user_tag"), UserTagDto[].class));
		List<BoardSongDto> boardSongDto = Arrays.asList(mapper.treeToValue(registerObj.get("playlist"), BoardSongDto[].class));
		
		long boardSeq = boardService.register(boardDto);
		
		boardService.registerSong(boardSongDto,boardSeq);
		boardService.registerTag(postTagDto, userTagDto, boardSeq);;
		
		
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/board")
	public ResponseEntity<?> getBoardOne(@Param("boardSeq") long boardSeq){
		
		Board board = boardService.getboardOne(boardSeq);
		Map<String, Object> result = new HashMap<String, Object>();
		if(board != null) {
			result.put("code",200);
			Map<String, Object> post = new HashMap<String, Object>();
			post.put("item", board);
			result.put("data", post);
		} else if(board == null){
			ResponseEntity.badRequest();
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/userSeq")
	public ResponseEntity<?> getfollowingBoard(@Param("userSeq") long userSeq){
		
		Map<String, Object> result = new HashMap<String, Object>();
		// 해당 유저의 팔로우 유저들을 찾아와서 포스트 검색하여 boardSeq 내림차순으로 정렬
		
		// 팔로우 중인 유저들 리스트 조회
		//List<follow> follows = followRepository.getfollowList(userSeq);
//		if(follows == null) {
//			// 팔로우 중인 사람 없음 표시
//		}
//		result.put("code",200);
//		for (int i = 0; i < follows.size(); i++) {
//			Map<String, Object> posts = new HashMap<String, Object>();
//			
//			List<Board> boards = boardService.getBoardAllByuserSeq(follows.get(i).getTouser());
//			List<BoardSong> boardSongs = boardService.getBoardSongAllByboardSeq();
//			
//			posts.put("item", boards);
//			posts.put("music", boardSongs);
//				
//			
//		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
