package com.common.dipping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.dipping.domain.dto.BoardDto;
import com.common.dipping.domain.dto.BoardSongDto;
import com.common.dipping.domain.dto.PostTagDto;
import com.common.dipping.domain.dto.UserTagDto;
import com.common.dipping.domain.entity.Board;
import com.common.dipping.domain.entity.BoardSong;
import com.common.dipping.domain.entity.PostTag;
import com.common.dipping.domain.entity.Tag;
import com.common.dipping.domain.entity.UserTag;
import com.common.dipping.repository.BoardRepository;
import com.common.dipping.repository.BoardSongRepository;
import com.common.dipping.repository.CommentRepository;
import com.common.dipping.repository.LikeRepository;
import com.common.dipping.repository.PostTagRepository;
import com.common.dipping.repository.TagRepository;
import com.common.dipping.repository.UserTagRepository;
import com.common.dipping.user.domain.User;
import com.common.dipping.user.repository.UserRepository;

@Service
public class BoardService {

	UserRepository userRepository;
	BoardRepository boardRepository;
	BoardSongRepository boardSongRepository;
	CommentRepository commentRepository;
	LikeRepository likeRepository;
	PostTagRepository postTagRepository;
	TagRepository tagRepository;
	UserTagRepository userTagRepository;

	public Board getboardOne(long boardSeq) {
		Board board = boardRepository.findAllByboardSeq(boardSeq);
		return board;
	}

	public long register(BoardDto boardDto) {

		User user = userRepository.findById(boardDto.getUserSeq());
		// 포스트 기본 설정
		Board board = Board.builder().content(boardDto.getContent())
				.openPost(boardDto.isOpenPost())
				.openComment(boardDto.isOpenComment())
				.albumart(boardDto.isAlbumart())
				.user(user).build();
		
		boardRepository.save(board);

		// 리턴은 사용자 기준 게시판 중에서 내림차순으로 했을 때 첫번째가 가장 최근에 만들어진
		// 게시판임으로 boardSeq 조회해서 반환
		return boardRepository.findTop1ByUserIdOrderByBoardSeqDesc(user);
	}

	public void registerSong(List<BoardSongDto> boardSongDto, long boardSeq) {

		for (int i = 0; i < boardSongDto.size(); i++) {
			if (boardSongDto.get(i) != null) {
				BoardSong boardSong = BoardSong.builder().songTitle(boardSongDto.get(i).getSongTitle())
						.songSinger(boardSongDto.get(i).getSongSinger()).songUrl(boardSongDto.get(i).getSongUrl())
						.songImgUrl(boardSongDto.get(i).getSongImgUrl())
						.board(boardRepository.findAllByboardSeq(boardSeq)).build();
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
				PostTag postTag = PostTag.builder().tag(tag).board(boardRepository.findAllByboardSeq(boardSeq)).build();
				postTagRepository.save(postTag);
			} else {
				return;
			}
		}

		for (int i = 0; i < userTagDto.size(); i++) {
			if (userTagDto.get(i) != null) {
				long userSeq = Long.parseLong(userTagDto.get(i).getContent());
				UserTag userTag = UserTag.builder().board(boardRepository.findAllByboardSeq(boardSeq))
						.user(userRepository.findById(userSeq)).build();
				userTagRepository.save(userTag);
			}else {
				return;
			}
		}

	}
}
