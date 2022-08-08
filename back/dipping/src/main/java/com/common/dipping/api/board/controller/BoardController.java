package com.common.dipping.api.board.controller;

import java.util.*;

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
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
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

    @GetMapping("/board")
    public ResponseEntity<?> getBoardOne(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestParam("boardId") int longId) {

        Long boardId = Long.valueOf(longId);
        Board board = boardService.getboardOne(boardId);
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        if (board != null) {
            result.put("code", 200);
            Map<String, Object> post = new HashMap<String, Object>();

            // item input
            BoardResponse boardResponse = new BoardResponse(board);
            Long one = Long.valueOf(1);
            boardResponse.setId(one);
            // boardResponse.setLikeCount(likeService.getCountByBoardSeq(board);
            // boardResponse.setMyLike(likeService.isMylike(userInfo.getId(), board));
            boardResponse.setCommentCount(commentService.getCountByBoardId(board));
            post.put("item", boardResponse);

            // music input
            List<BoardSong> boardSongs = boardService.getBoardSongAllById(board);

            if (boardSongs != null) {
                post.put("music", boardSongs);
            }

            data.put("post", post);
            result.put("data", data);
        } else if (board == null) {
            ResponseEntity.badRequest(); // 잘못된 요청
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getfollowingBoard(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestParam("pageNum") int pageNum) {

        Map<String, Object> result = new HashMap<String, Object>();
        // 해당 유저의 팔로우 유저들을 찾아와서 포스트 검색하여 Id 내림차순으로 정렬

        result.put("code", 200);
        List<Follow> follows = followService.getfollowListByFromUser(userInfo.getId());

        List<Board> posting = new ArrayList<>();

        for (Follow f : follows) {
            List<Board> boards = boardService.getBoardByUserId(f.getReceiver());
            for (Board b : boards) {
                posting.add(b);
            }
        }

        Collections.sort(posting, new boardIdCompare());

        List<Object> posts = new ArrayList<>();
        for (int i = pageNum - 5; i < pageNum; i++) {
            JSONArray jArray = new JSONArray();
            if (posting.get(i) != null) {
                Map<String,Object> item = new HashMap<>();
                BoardResponse boardResponse = new BoardResponse(posting.get(i));
                Long num = Long.valueOf(i) + 1;
                boardResponse.setId(num);
                // boardResponse.setLikeCount(likeService.getCountByBoardSeq(boards.get(i)));
                // boardResponse.setMyLike(likeService.isMylike(userSeq, boards.get(i)));
                boardResponse.setCommentCount(commentService.getCountByBoardId(posting.get(i)));
                item.put("item", boardResponse);
                jArray.add(item);

                List<BoardSong> boardSongs = boardService.getBoardSongAllById(posting.get(i));

                if (boardSongs != null) {
                    Map<String,Object> music = new HashMap<>();
                    music.put("music", boardSongs);
                    jArray.add(music);
                }

            } else {
                break;
            }
            posts.add(jArray);
        }
        result.put("data", posts);

//        if (result.get("posts") == null) {
//
//        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

	@PostMapping("/comment")
	public ResponseEntity<?> registerComment(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody CommentDto commentDto) {
		commentDto.setUserId(userInfo.getId());
		Long commentId = commentService.registerComment(commentDto);

		if(commentId == 0L){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/comment")
	public ResponseEntity<?> getCommentByBoardId(@Param("boardId") Long boardId){
		List<Comment> comments = commentService.getlistCommentByboardId(boardId);
		Map<String, Object> result = new HashMap<String, Object>();
		if(!comments.isEmpty()){
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
