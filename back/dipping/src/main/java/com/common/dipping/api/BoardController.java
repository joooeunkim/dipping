package com.common.dipping.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.common.dipping.domain.dto.BoardDto;
import com.common.dipping.domain.dto.BoardSongDto;
import com.common.dipping.domain.entity.Board;
import com.common.dipping.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;

@RestController("/api/board")
@RequiredArgsConstructor
public class BoardController {
	
	@Autowired
	BoardService boardService;

	@PostMapping
	public ResponseEntity<?> regist(@RequestBody ObjectNode registerObj) throws JsonProcessingException, IllegalArgumentException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		BoardDto boardDto = mapper.treeToValue(registerObj.get("post"), BoardDto.class);
		List<BoardSongDto> boardSongDto = (List<BoardSongDto>) mapper.treeToValue(registerObj.get("playlist"), BoardSongDto.class);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getBoardOne(@Param("boardSeq") long boardSeq){
		
		Board board = boardService.getboardOne(boardSeq);
		Map<String, Object> result = new HashMap<String, Object>();
		if(board != null) {
			result.put("code",200);
			Map<String, Object> post = new HashMap<String, Object>();
			post.put("item", board);
			result.put("data", post);
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
