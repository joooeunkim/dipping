package com.common.dipping.api.board.service;

import com.common.dipping.api.board.domain.dto.HeartDto;
import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Comment;
import com.common.dipping.api.board.domain.entity.Heart;
import com.common.dipping.api.board.repository.BoardRepository;
import com.common.dipping.api.board.repository.CommentRepository;
import com.common.dipping.api.board.repository.HeartRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 내가 좋아요를 눌렀는지 안눌렀는지 반환
    public boolean isMylikeByBoardId(Long userId, Board board) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id="+userId));

        boolean mylike = heartRepository.existsByUserAndBoard(user,board);
        return mylike;
    }

    public boolean isMylikeByCommentId(Long userId, Comment comment) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id="+userId));
        boolean mylike = heartRepository.existsByUserAndComment(user,comment);
        return mylike;
    }

    // 포스트번호의 좋아요 개수 가져오기
    public int getCountByBoardId(Board board) {
        return heartRepository.findHeartsCountByBoardId(board.getId());
    }

    public int getCountByCommentId(Comment comment) {
        return heartRepository.findHeartsCountByCommentId(comment.getId());
    }

    // 유저아이디와 포스트번호를 통해 좋아요가 눌러져있다면 삭제 없다면 등록
    public int setHeartByUserIdAndBoardId(Long userId, Long boardId){
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id="+userId));
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+boardId));

        boolean mylike = heartRepository.existsByUserAndBoard(user,board);
        if(!mylike){
            Heart heart = Heart.builder()
                    .user(user)
                    .board(board)
                    .build();
            heartRepository.save(heart);
            return 1; // 좋아요 누름
        }else {
            Optional<Heart> heartopt = heartRepository.findByUserAndBoard(user,board);
            if(!heartopt.isEmpty()){
                Heart heart = heartopt.get();
                heartRepository.deleteById(heart.getId());
                return 0; // 좋아요 삭제
            }else {
                return 2; // 오류 발생
            }
        }
    }

    // 유저아이디와 댓글 번호를 통해 좋아요가 눌러져있다면 삭제 없다면 등록
    public int setHeartByUserIdAndCommentId(Long userId, Long commentId){
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id="+userId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다. id="+commentId));

        boolean mylike = heartRepository.existsByUserAndComment(user,comment);
        if(!mylike){
            Heart heart = Heart.builder()
                    .user(user)
                    .comment(comment)
                    .build();
            heartRepository.save(heart);
            return 1; // 좋아요 누름
        }else {
            Optional<Heart> heartopt = heartRepository.findByUserAndComment(user,comment);
            if(!heartopt.isEmpty()){
                Heart heart = heartopt.get();
                heartRepository.deleteById(heart.getId());
                return 0; // 좋아요 삭제
            }else {
                return 2; // 오류 발생
            }
        }
    }

    // 포스트의 좋아요 누른 사람 목록
    public List<HeartDto> getListByBoardId(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+boardId));
        List<Heart> hearts = heartRepository.findAllByBoard(board).orElse(new ArrayList<>());

        List<HeartDto> heartDtos = new ArrayList<>();
        if(!hearts.isEmpty()){

            for (Heart h: hearts) {
                HeartDto heartDto = new HeartDto();
                heartDto.setId(h.getId());
                heartDto.setBoardId(h.getBoard().getId());
                heartDto.setCommentId(h.getComment().getId());
                heartDto.setUserId(h.getUser().getId());

                heartDtos.add(heartDto);
            }
        }

        return heartDtos;
    }

    public List<HeartDto> getListByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+commentId));
        List<Heart> hearts = heartRepository.findAllByComment(comment).orElse(new ArrayList<>());

        List<HeartDto> heartDtos = new ArrayList<>();
        if(!hearts.isEmpty()){

            for (Heart h: hearts) {
                HeartDto heartDto = new HeartDto();
                heartDto.setId(h.getId());
                heartDto.setBoardId(h.getBoard().getId());
                heartDto.setCommentId(h.getComment().getId());
                heartDto.setUserId(h.getUser().getId());

                heartDtos.add(heartDto);
            }
        }

        return heartDtos;
    }
}
