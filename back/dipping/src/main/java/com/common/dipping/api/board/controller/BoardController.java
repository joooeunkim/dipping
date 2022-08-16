package com.common.dipping.api.board.controller;

import java.util.*;

import com.common.dipping.api.alarm.service.AlarmService;
import com.common.dipping.api.board.service.HeartService;
import com.common.dipping.api.search.service.SearchService;
import com.common.dipping.api.user.repository.StorageRepository;
import com.common.dipping.api.user.service.StorageService;
import com.common.dipping.api.user.service.UserService;
import com.common.dipping.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final StorageService storageService;
    private final AlarmService alarmService;
    private final UserService userService;

    static class boardIdCompare implements Comparator<Board> {
        @Override
        public int compare(Board b1, Board b2) {
            return (int) (b2.getId() - b1.getId());
        }
    }

    @Operation(summary = "피드 등록", description = "피드 내용과, 포스트 태그, 유저 태그, 음악 정보를 입력")
    @PostMapping
    public ResponseEntity<?> register(@AuthenticationPrincipal UserDetailsImpl userInfo,@RequestBody ObjectNode registerObj)
            throws JsonProcessingException, IllegalArgumentException {

        ObjectMapper mapper = new ObjectMapper();

        BoardDto boardDto = mapper.treeToValue(registerObj.get("post"), BoardDto.class);
        boardDto.setUserId(userInfo.getId());
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

    @Operation(summary = "피드 삭제", description = "피드 아이디를 통해 삭제 요청")
    @DeleteMapping
    public ResponseEntity<?> deleteBoard(@Param("boardId") Long boardId){
        boolean result = boardService.deleteBoard(boardId);
        if(!result){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 수정", description = "피드 내용을 전송시 덮어쓰기를 통해 수정")
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "단일 피드 조회 성공", content = @Content(schema = @Schema(implementation = BoardResponse.class ))) })
    @Operation(summary = "피드 단일 조회", description = "boardId 입력시 단일 조회하여 피드 정보와 음악 정보를 조회")
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로잉 피드 목록 조회 성공", content = @Content(schema = @Schema(implementation = BoardResponse.class ))) })
    @Operation(summary = "팔로잉 피드 목록 조회", description = "요청 유저가 팔로잉 중인 유저들의 최근 7일간의 피드 정보와 음악 정보를 조회")
    @GetMapping("/user")
    public ResponseEntity<?> getfollowingBoard(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestParam(name = "pageNum", required = false,defaultValue = "1") int pageNum) {

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
        int page = pageNum * 10;
        for (int i = page - 10; i < page; i++) {
            if ( i < posting.size()) {
                Map<String,Object> item = new HashMap<>();
                BoardResponse boardResponse = new BoardResponse(posting.get(i));
                Long num = Long.valueOf(i) + 1;
                boardResponse.setId(num);
                boardResponse.setLikeCount(heartService.getCountByBoardId(posting.get(i)));
                boardResponse.setMyLike(heartService.isMylikeByBoardId(userInfo.getId(), posting.get(i)));
                boardResponse.setCommentCount(commentService.getCountByBoardId(posting.get(i)));
                item.put("item", boardResponse);

                List<BoardSong> boardSongs = boardService.getBoardSongAllById(posting.get(i));
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
                    item.put("music", boardSongDtos);
                }
                posts.add(item);
            } else {
                break;
            }
        }
        result.put("data", posts);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천 피드 목록 조회 성공", content = @Content(schema = @Schema(implementation = BoardResponse.class ))) })
    @Operation(summary = "추천 피드 목록 조회", description = "요청 유저의 취향을 통해 피드 정보와 음악 정보를 조회")
    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendBoard(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestParam(name = "pageNum", required = false,defaultValue = "1") int pageNum) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> data = new HashMap<>();

        HashSet<BoardResponse> boardSet = boardService.RecommednBoard(userInfo.getId());
        List<BoardResponse> boardList = new ArrayList<>(boardSet);
        result.put("code", 200);

        List<Object> posts = new ArrayList<>();
        int page = pageNum * 10;
        for (int i = page - 10; i < page; i++) {
            if (i < boardList.size()) {
                Map<String,Object> item = new HashMap<>();
                BoardResponse boardResponse = boardList.get(i);
                Long num = Long.valueOf(i) + 1;
                boardResponse.setId(num);

                item.put("item", boardResponse);
                Board board = boardService.getboardOne(boardResponse.getBoardId());
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
                    item.put("music", boardSongDtos);
                }
                posts.add(item);
            } else {
                break;
            }
        }
        data.put("posts", posts);
        result.put("data", data);

        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "댓글 등록", description = "댓글 내용과 boardId를 받아 해당 피드에 댓글을 등록")
	@PostMapping("/comment")
	public ResponseEntity<?> registerComment(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        CommentDto commentDto = mapper.treeToValue(registerObj.get("comment"), CommentDto.class);
        commentDto.setUserId(userInfo.getId());
		Long commentId = commentService.registerComment(commentDto);

		if (commentDto.getParentId() == 0) {
            Long receiverId = userService.findByBoard(commentDto.getBoardId()).getId();
            alarmService.alarmBySenderIdAndReceiverIdAndAlarmType(userInfo.getId(), receiverId, "Comment");
        } else {
            Long receiverId = commentService.findById(commentDto.getParentId()).getUser().getId();
            alarmService.alarmBySenderIdAndReceiverIdAndAlarmType(userInfo.getId(), receiverId, "ReComment");
        }

		if(commentId == 0L){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "피드 댓글 목록 조회 성공", content = @Content(schema = @Schema(implementation = CommentDto.class ))) })
    @Operation(summary = "피드 댓글 목록 조회", description = "boardId를 통해 해당 피드의 댓글 조회")
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

    @Operation(summary = "피드 댓글 목록 삭제", description = "commentId를 입력받아 해당 댓글 삭제")
    @DeleteMapping("comment")
    public ResponseEntity<?> deleteComment(@Param("commentId") Long commentId){
        boolean result = commentService.deleteComment(commentId);
        if(!result){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "피드 댓글 목록 수정", description = "commentId를 통해 해당 댓글 수정")
    @PostMapping("/comment/edit")
    public ResponseEntity<?> editComment(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        CommentDto commentDto = mapper.treeToValue(registerObj.get("comment"), CommentDto.class);
        //commentDto.setUserId(userInfo.getId());
        commentService.editComment(commentDto);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Operation(summary = "피드 좋아요/좋아요취소", description = "boardId를 통해 해당 피드 좋아요 혹은 좋아요 취소")
    @PostMapping("/like")
    public ResponseEntity<?> likeUnlike(@AuthenticationPrincipal UserDetailsImpl userInfo,@RequestBody ObjectNode registerObj) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();


        HeartDto heartDto = mapper.treeToValue(registerObj.get("postLike"), HeartDto.class);
        int result = -1;
        Long receiverId = 0L;
        String alarmType = "";
        // 초기값인 경우
        if(heartDto.getBoardId() > 0L){
            result = heartService.setHeartByUserIdAndBoardId(userInfo.getId(),heartDto.getBoardId());
            receiverId = userService.findByBoard(heartDto.getBoardId()).getId();
            alarmType = "BoardLike" + heartDto.getBoardId().toString();
        } else if(heartDto.getCommentId() > 0L){
            result = heartService.setHeartByUserIdAndCommentId(userInfo.getId(),heartDto.getCommentId());
            receiverId = userService.findByComment(heartDto.getCommentId()).getId();
            alarmType = "CommentLike" + heartDto.getCommentId().toString();
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        if (result == 1 || result == 0){
            if (result == 1) {
                alarmService.alarmBySenderIdAndReceiverIdAndAlarmType(userInfo.getId(), receiverId, alarmType);
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "피드 좋아요 목록 조회 성공", content = @Content(schema = @Schema(implementation = HeartDto.class ))) })
    @Operation(summary = "피드 좋아요 목록 조회", description = "boardId를 통해 해당 피드의 좋아요 조회")
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

    @Operation(summary = "피드 보관함 추가", description = "boardId를 통해 해당 피드를 사용자의 보관함에 추가")
    @PostMapping("/collection")
    public ResponseEntity<?> collectionBoard(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();


        StorageDto storageDto = mapper.treeToValue(registerObj.get("collection"), StorageDto.class);
        storageService.storageBoard(storageDto.getUserId(),storageDto.getBoardId());

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Operation(summary = "보관함에서 피드 삭제", description = "해당 유저의 보관함에 있는 피드 삭제")
    @DeleteMapping("/collection")
    public ResponseEntity<?> deletecollection(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        StorageDto storageDto = mapper.treeToValue(registerObj.get("collection"), StorageDto.class);
        storageService.deletStorage(storageDto.getUserId(),storageDto.getBoardId());

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
