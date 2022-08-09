package com.common.dipping.api.board.service;

import java.time.LocalDateTime;
import java.util.List;

import com.common.dipping.api.user.domain.entity.Follow;
import org.springframework.stereotype.Service;

import com.common.dipping.api.board.domain.dto.BoardDto;
import com.common.dipping.api.board.domain.dto.BoardSongDto;
import com.common.dipping.api.board.domain.dto.PostTagDto;
import com.common.dipping.api.board.domain.dto.UserTagDto;
import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.BoardSong;
import com.common.dipping.api.board.domain.entity.PostTag;
import com.common.dipping.api.board.domain.entity.Tag;
import com.common.dipping.api.board.domain.entity.UserTag;
import com.common.dipping.api.board.repository.BoardRepository;
import com.common.dipping.api.board.repository.BoardSongRepository;
import com.common.dipping.api.board.repository.PostTagRepository;
import com.common.dipping.api.board.repository.TagRepository;
import com.common.dipping.api.board.repository.UserTagRepository;
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

	public Board getboardOne(Long boardId) {
		Board board = boardRepository.findById(boardId).orElse(null);
		return board;
	}

	public Board register(BoardDto boardDto) {

		User user = userRepository.findById(boardDto.getUserId()).orElse(null);
		// 포스트 기본 설정
		Board board = Board.builder().content(boardDto.getContent())
				.openPost(boardDto.isOpenPost())
				.openComment(boardDto.isOpenComment())
				.albumArt(boardDto.isAlbumArt())
				.user(user).build();
		
		return boardRepository.save(board);

		// 리턴은 사용자 기준 게시판 중에서 내림차순으로 했을 때 첫번째가 가장 최근에 만들어진
		// 게시판임으로 boardSeq 조회해서 반환
	}

	public void registerSong(List<BoardSongDto> boardSongDto, Board board) {

		for (int i = 0; i < boardSongDto.size(); i++) {
			if (boardSongDto.get(i) != null) {
				BoardSong boardSong = BoardSong.builder().songTitle(boardSongDto.get(i).getSongTitle())
						.songSinger(boardSongDto.get(i).getSongSinger()).songUrl(boardSongDto.get(i).getSongUrl())
						.songImgUrl(boardSongDto.get(i).getSongImgUrl())
						.board(board).build();
				boardSongRepository.save(boardSong);
			}
		}

		// 게시판과 연결
	}

	public void registerTag(List<PostTagDto> postTagDto, List<UserTagDto> userTagDto, Board board) {

		for (int i = 0; i < postTagDto.size(); i++) {
			if (postTagDto.get(i) != null) {
				Tag tag = tagRepository.findByContent(postTagDto.get(i).getContent());
				if (tag != null) {
					postTagDto.get(i).setTagId(tag.getId());
				} else {
					tag = Tag.builder().content(postTagDto.get(i).getContent()).build();
					tagRepository.save(tag);
					tag = tagRepository.findByContent(postTagDto.get(i).getContent());
					postTagDto.get(i).setTagId(tag.getId());
				}
				PostTag postTag = PostTag.builder().tag(tag).board(board).build();
				postTagRepository.save(postTag);
			} else {
				return;
			}
		}

		for (int i = 0; i < userTagDto.size(); i++) {
			if (userTagDto.get(i) != null) {
				long userId = Long.parseLong(userTagDto.get(i).getContent());
				User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id="+userId));
				UserTag userTag = UserTag.builder().board(board)
						.user(user).build();
				userTagRepository.save(userTag);
			}else {
				return;
			}
		}

	}

	public List<Board> getBoardByUserId(User reciveuser){
		//User user = userRepository.findById(reciveuser.getId()).orElse(null);
		// 최근 일주일안에 생성된 포스트만 가져온다.
		LocalDateTime week = LocalDateTime.now();
		List<Board> list = boardRepository.findAllWithUserIdAndCreatedAtAfter(reciveuser.getId(),week.minusDays(7));
		return list;
	}

	public List<BoardSong> getBoardSongAllById(Board board) {
		List<BoardSong> list = boardSongRepository.findBoardSongByBoardId(board.getId());
		return list;
	}
	
}
