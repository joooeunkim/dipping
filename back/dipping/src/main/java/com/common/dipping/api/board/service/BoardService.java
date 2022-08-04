package com.common.dipping.api.board.service;

import java.util.List;

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
	//private final CommentRepository commentRepository;
	//private final LikeRepository likeRepository;
	private final PostTagRepository postTagRepository;
	private final TagRepository tagRepository;
	private final UserTagRepository userTagRepository;

	public Board getboardOne(long boardSeq) {
		Board board = boardRepository.findByboardSeq(boardSeq);
		return board;
	}

	public long register(BoardDto boardDto) {

		User user = userRepository.findById(boardDto.getUserSeq()).orElse(null);
		// 포스트 기본 설정
		Board board = Board.builder().content(boardDto.getContent())
				.openPost(boardDto.isOpenPost())
				.openComment(boardDto.isOpenComment())
				.albumart(boardDto.isAlbumart())
				.user(user).build();
		
		return boardRepository.save(board).getBoardSeq();

		// 리턴은 사용자 기준 게시판 중에서 내림차순으로 했을 때 첫번째가 가장 최근에 만들어진
		// 게시판임으로 boardSeq 조회해서 반환
	}

	public void registerSong(List<BoardSongDto> boardSongDto, long boardSeq) {

		for (int i = 0; i < boardSongDto.size(); i++) {
			if (boardSongDto.get(i) != null) {
				BoardSong boardSong = BoardSong.builder().songTitle(boardSongDto.get(i).getSongTitle())
						.songSinger(boardSongDto.get(i).getSongSinger()).songUrl(boardSongDto.get(i).getSongUrl())
						.songImgUrl(boardSongDto.get(i).getSongImgUrl())
						.board(boardRepository.findByboardSeq(boardSeq)).build();
			}
		}

		// 게시판과 연결
	}

	public void registerTag(List<PostTagDto> postTagDto, List<UserTagDto> userTagDto, long boardSeq) {

		for (int i = 0; i < postTagDto.size(); i++) {
			if (postTagDto.get(i) != null) {
				Tag tag = tagRepository.findByContent(postTagDto.get(i).getContent());
				if (tag != null) {
					postTagDto.get(i).setTagSeq(tag.getTagSeq());
				} else {
					tag = Tag.builder().content(postTagDto.get(i).getContent()).build();
					tagRepository.save(tag);
					tag = tagRepository.findByContent(postTagDto.get(i).getContent());
					postTagDto.get(i).setTagSeq(tag.getTagSeq());
				}
				PostTag postTag = PostTag.builder().tag(tag).board(boardRepository.findByboardSeq(boardSeq)).build();
				postTagRepository.save(postTag);
			} else {
				return;
			}
		}

		for (int i = 0; i < userTagDto.size(); i++) {
			if (userTagDto.get(i) != null) {
				long userSeq = Long.parseLong(userTagDto.get(i).getContent());
				User user = userRepository.findById(userSeq).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+userSeq));
				UserTag userTag = UserTag.builder().board(boardRepository.findByboardSeq(boardSeq))
						.user(user).build();
				userTagRepository.save(userTag);
			}else {
				return;
			}
		}

	}
}
