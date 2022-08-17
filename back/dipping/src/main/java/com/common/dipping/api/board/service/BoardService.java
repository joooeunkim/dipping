package com.common.dipping.api.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.common.dipping.api.board.domain.dto.*;
import com.common.dipping.api.board.domain.entity.*;
import com.common.dipping.api.board.repository.*;
import com.common.dipping.api.user.domain.entity.Follow;
import org.springframework.stereotype.Service;

import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardSongRepository boardSongRepository;
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;
    private final UserTagRepository userTagRepository;
    private final InterestTagRepository interestTagRepository;
    private final HeartService heartService;
    private final CommentService commentService;

    public Board getboardOne(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + boardId));

        return board;
    }

    public Board register(BoardDto boardDto) {

        User user = userRepository.findById(boardDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + boardDto.getUserId()));
        // 포스트 기본 설정
        Board board = Board.builder().content(boardDto.getContent())
                .openPost(boardDto.isOpenPost())
                .openComment(boardDto.isOpenComment())
                .albumArt(boardDto.isAlbumArt())
                .user(user).build();

        return boardRepository.save(board);
    }

	public Board edit(BoardDto boardDto){
		User user = userRepository.findById(boardDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + boardDto.getUserId()));
		Board board = boardRepository.findById(boardDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + boardDto.getId()));;
		board.update(boardDto.getContent(),boardDto.isOpenPost(),boardDto.isOpenComment(),boardDto.isAlbumArt());
		return boardRepository.save(board);
	}

    public void editSong(List<BoardSongDto> boardSongDto, Board board){
        boardSongRepository.deleteAllByBoard(board);
        registerSong(boardSongDto,board);
    }

    public void editTag(List<PostTagDto> postTagDto, List<UserTagDto> userTagDto, Board board){
        postTagRepository.deleteAllByBoard(board);
        userTagRepository.deleteAllByBoard(board);
        registerTag(postTagDto,userTagDto,board);
    }

    public void registerSong(List<BoardSongDto> boardSongDto, Board board) {

        if (!boardSongDto.isEmpty()) {
            for (int i = 0; i < boardSongDto.size(); i++) {
                if (boardSongDto.get(i) != null) {
                    BoardSong boardSong = BoardSong.builder().songTitle(boardSongDto.get(i).getSongTitle())
                            .songSinger(boardSongDto.get(i).getSongSinger()).songUrl(boardSongDto.get(i).getSongUrl())
                            .songImgUrl(boardSongDto.get(i).getSongImgUrl())
                            .board(board).build();
                    boardSongRepository.save(boardSong);
                }
            }
        }

    }

    public void registerTag(List<PostTagDto> postTagDto, List<UserTagDto> userTagDto, Board board) {

        if (!postTagDto.isEmpty()) {
            for (int i = 0; i < postTagDto.size(); i++) {
                Tag tag = tagRepository.findByContent(postTagDto.get(i).getContent());
                if (tag != null) {
                    postTagDto.get(i).setTagId(tag.getId());
                } else {
                    tag = Tag.builder().content(postTagDto.get(i).getContent()).build();
                    tag = tagRepository.save(tag);
                }
                PostTag postTag = PostTag.builder().tag(tag).board(board).build();
                postTagRepository.save(postTag);
            }
        }

        if (!userTagDto.isEmpty()) {
            for (int i = 0; i < userTagDto.size(); i++) {
                long userId = Long.parseLong(userTagDto.get(i).getContent());
                User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
                UserTag userTag = UserTag.builder().board(board)
                        .user(user).build();
                userTagRepository.save(userTag);
            }
        }

    }

    public List<Board> getBoardByUserId(User reciveuser) {
        //User user = userRepository.findById(reciveuser.getId()).orElse(null);
        // 최근 일주일안에 생성된 포스트만 가져온다.
        LocalDateTime week = LocalDateTime.now();
        List<Board> list = boardRepository.findAllWithUserIdAndCreatedAtAfter(reciveuser.getId(), week.minusDays(7));
        return list;
    }

    public List<Board> getAllBoardsByUserId(Long userId) {
        List<Board> boardList = boardRepository.findAllByUserId(userId);
        return boardList;
    }

    public List<ProfilePostDto> getAllBoardByUserId(Long userId){
        List<Board> boardList = boardRepository.findAllByUserId(userId);
        List<ProfilePostDto> list = new ArrayList<>();
        for(Board board: boardList){
            List<BoardSong> boardSongList = getBoardSongAllById(board);
            String songImgUrl = boardSongList.get(0).getSongImgUrl();
            list.add(new ProfilePostDto(board.getId(), songImgUrl));
        }
        return list;
    }

    public List<BoardSong> getBoardSongAllById(Board board) {
        List<BoardSong> list = boardSongRepository.findBoardSongByBoardId(board.getId());
        return list;
    }

    public boolean deleteBoard(Long boardId,Long userId) {
        boardRepository.deleteByIdAndUserId(boardId,userId);
        return boardRepository.existsById(boardId);
    }

    public HashSet<BoardResponse> RecommednBoard(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        List<InterestTag> interestTags = interestTagRepository.findAllByUser(user);
        List<Long> tags = new ArrayList<>();
        for ( InterestTag i: interestTags) {
            tags.add(i.getTag().getId());
        }
        List<PostTag> postTags = postTagRepository.findByTag(tags);

        List<Long> boards = new ArrayList<>();
        for ( PostTag p: postTags) {
            boards.add(p.getBoard().getId());
        }
        List<Board> boardList = boardRepository.findListById(boards);
        HashSet<BoardResponse> boardResponses = new HashSet<>();

        for (Board b: boardList) {
            BoardResponse boardResponse = new BoardResponse(b);
            boardResponse.setLikeCount(heartService.getCountByBoardId(b));
            boardResponse.setMyLike(heartService.isMylikeByBoardId(userId, b));
            boardResponse.setCommentCount(commentService.getCountByBoardId(b));
            boardResponses.add(boardResponse);
        }
        return boardResponses;
    }

    public List<String> getPostTagByBoard(Board board) {
        List<PostTag> postTags = postTagRepository.findAllByBoard(board);
        List<String> postTagDtos = new ArrayList<>();
        for (PostTag p: postTags) {
            postTagDtos.add(p.getTag().getContent());
        }
        return postTagDtos;
    }
}
