package com.common.dipping.api.board.controller;

import java.util.*;

import com.common.dipping.api.board.service.HeartService;
import com.common.dipping.security.UserDetailsImpl;
import net.minidev.json.JSONArray;

import com.common.dipping.api.board.domain.dto.*;
import com.common.dipping.api.board.domain.entity.Comment;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    private final HeartService heartService;
    private final FollowService followService;

    static class boardIdCompare implements Comparator<Board> {
        @Override
        public int compare(Board b1, Board b2) {
            return (int) (b2.getId() - b1.getId());
        }
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody ObjectNode registerObj)
            throws JsonProcessingException, IllegalArgumentException {

        ObjectMapper mapper = new ObjectMapper();

        BoardDto boardDto = mapper.treeToValue(registerObj.get("post"), BoardDto.class);

        List<PostTagDto> postTagDto = Arrays
                .asList(mapper.treeToValue(registerObj.get("post_tag"), PostTagDto[].class));
        List<UserTagDto> userTagDto = Arrays
                .asList(mapper.treeToValue(registerObj.get("user_tag"), UserTagDto[].class));
        List<BoardSongDto> boardSongDto = Arrays
                .asList(mapper.treeToValue(registerObj.get("playlist"), BoardSongDto[].class));

        Board newboard = boardService.register(boardDto);
        boardService.registerSong(boardSongDto, newboard);
        boardService.registerTag(postTagDto, userTagDto, newboard);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBoard(@Param("boardId") Long boardId){
        boolean result = boardService.deleteBoard(boardId);
        if(!result){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editBoard(@RequestBody ObjectNode registerObj)
            throws JsonProcessingException, IllegalArgumentException {

        ObjectMapper mapper = new ObjectMapper();

        BoardDto boardDto = mapper.treeToValue(registerObj.get("post"), BoardDto.class);

        List<PostTagDto> postTagDto = Arrays
                .asList(mapper.treeToValue(registerObj.get("post_tag"), PostTagDto[].class));
        List<UserTagDto> userTagDto = Arrays
                .asList(mapper.treeToValue(registerObj.get("user_tag"), UserTagDto[].class));
        List<BoardSongDto> boardSongDto = Arrays
                .asList(mapper.treeToValue(registerObj.get("playlist"), BoardSongDto[].class));

        Board newboard = boardService.edit(boardDto);
        boardService.editSong(boardSongDto, newboard);
        boardService.editTag(postTagDto, userTagDto, newboard);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/board")
    public ResponseEntity<?> getBoardOne(@AuthenticationPrincipal UserDetailsImpl userInfo, @Param("boardId") Long boardId) {

        Board board = boardService.getboardOne(boardId);
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        if (board.getId() >= 0) {
            result.put("code", 200);
            Map<String, Object> post = new HashMap<String, Object>();

            // item input
            BoardResponse boardResponse = new BoardResponse(board);
            Long one = Long.valueOf(1);
            boardResponse.setId(one);
            boardResponse.setLikeCount(heartService.getCountByBoardId(board));
            boardResponse.setMyLike(heartService.isMylikeByBoardId(userInfo.getId(), board));
            boardResponse.setCommentCount(commentService.getCountByBoardId(board));
            post.put("item", boardResponse);

            // music input
            List<BoardSong> boardSongs = boardService.getBoardSongAllById(board);
            List<BoardSongDto> boardSongDtos = new ArrayList<>();
            if (!boardSongs.isEmpty()) {
                for (BoardSong boardSong:boardSongs ) {
                    BoardSongDto boardSongDto = new BoardSongDto();
                    boardSongDto.setId(boardSong.getId());
                    boardSongDto.setBoardId(boardSong.getBoard().getId());
                    boardSongDto.setSongTitle(boardSong.getSongTitle());
                    boardSongDto.setSongSinger(boardSong.getSongSinger());
                    boardSongDto.setSongUrl(boardSong.getSongUrl());
                    boardSongDto.setSongImgUrl(boardSong.getSongImgUrl());
                    boardSongDtos.add(boardSongDto);
                }
                post.put("music", boardSongDtos);
            }

            data.put("post", post);
            result.put("data", data);
        } else if (board.getId() == 0L) {
            ResponseEntity.badRequest(); // 잘못된 요청
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getfollowingBoard(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestParam(name = "pageNum", required = false,defaultValue = "5") int pageNum) {

        Map<String, Object> result = new HashMap<String, Object>();
        // 해당 유저의 팔로우 유저들을 찾아와서 포스트 검색하여 Id 내림차순으로 정렬
        List<Follow> follows = followService.getfollowListByFromUser(userInfo.getId());
        if(follows.isEmpty()){
            result.put("code", 201);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        List<Board> posting = new ArrayList<>();

        for (Follow f : follows) {
            List<Board> boards = boardService.getBoardByUserId(f.getReceiver());
            for (Board b : boards) {
                posting.add(b);
            }
        }

        if(posting.isEmpty()){
            result.put("code", 201);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        result.put("code", 200);

        Collections.sort(posting, new boardIdCompare());

        List<Object> posts = new ArrayList<>();
        for (int i = pageNum - 5; i < pageNum; i++) {
            JSONArray jArray = new JSONArray();
            if ( i < posting.size()) {
                Map<String,Object> item = new HashMap<>();
                BoardResponse boardResponse = new BoardResponse(posting.get(i));
                Long num = Long.valueOf(i) + 1;
                boardResponse.setId(num);
                boardResponse.setLikeCount(heartService.getCountByBoardId(posting.get(i)));
                boardResponse.setMyLike(heartService.isMylikeByBoardId(userInfo.getId(), posting.get(i)));
                boardResponse.setCommentCount(commentService.getCountByBoardId(posting.get(i)));
                item.put("item", boardResponse);
                jArray.add(item);

                List<BoardSong> boardSongs = boardService.getBoardSongAllById(posting.get(i));
                List<BoardSongDto> boardSongDtos = new ArrayList<>();
                if (!boardSongs.isEmpty()) {
                    Map<String,Object> music = new HashMap<>();
                    for (BoardSong boardSong:boardSongs ) {
                        BoardSongDto boardSongDto = new BoardSongDto();
                        boardSongDto.setId(boardSong.getId());
                        boardSongDto.setBoardId(boardSong.getBoard().getId());
                        boardSongDto.setSongTitle(boardSong.getSongTitle());
                        boardSongDto.setSongSinger(boardSong.getSongSinger());
                        boardSongDto.setSongUrl(boardSong.getSongUrl());
                        boardSongDto.setSongImgUrl(boardSong.getSongImgUrl());
                        boardSongDtos.add(boardSongDto);
                    }
                    music.put("music", boardSongDtos);
                    jArray.add(music);
                }

            } else {
                break;
            }
            posts.add(jArray);
        }
        result.put("data", posts);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

	@PostMapping("/comment")
	public ResponseEntity<?> registerComment(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();


        CommentDto commentDto = mapper.treeToValue(registerObj.get("comment"), CommentDto.class);
        //commentDto.setUserId(userInfo.getId());
		Long commentId = commentService.registerComment(commentDto);

		if(commentId == 0L){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/comment")
	public ResponseEntity<?> getCommentByBoardId(@AuthenticationPrincipal UserDetailsImpl userInfo, @Param("boardId") Long boardId){
		List<CommentDto> commentDtos = commentService.getlistCommentByboardId(userInfo.getId(), boardId);
		Map<String, Object> result = new HashMap<String, Object>();
		if(!commentDtos.isEmpty()){
			result.put("code", 200);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("comment", commentDtos);
			result.put("data",data);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}else {
			result.put("code",201);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
	}

    @PostMapping("/like")
    public ResponseEntity<?> likeUnlike(@AuthenticationPrincipal UserDetailsImpl userInfo,@RequestBody ObjectNode registerObj) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();


        HeartDto heartDto = mapper.treeToValue(registerObj.get("postLike"), HeartDto.class);
        int result = -1;
        // 초기값인 경우
        if(heartDto.getBoardId() > 0L){
            result = heartService.setHeartByUserIdAndBoardId(userInfo.getId(),heartDto.getBoardId());
        } else if(heartDto.getCommentId() > 0L){
            result = heartService.setHeartByUserIdAndCommentId(userInfo.getId(),heartDto.getCommentId());
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        if (result == 1 || result == 0){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/like")
    public ResponseEntity<?> getCommentByBoardId(@RequestParam(name = "boardId", required = false,defaultValue = "0") Long boardId, @RequestParam(name = "commentId", required = false,defaultValue = "0") Long commentId){
        List<HeartDto> heartDtos = new ArrayList<>();
        if(boardId > 0L) {
            heartDtos = heartService.getListByBoardId(boardId);
        }else if( commentId > 0L){
            heartDtos = heartService.getListByCommentId(commentId);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if(!heartDtos.isEmpty()){
            result.put("code", 200);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("likes", heartDtos);
            result.put("data",data);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            result.put("code",201);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }
    
}
