package com.common.dipping.api.board.service;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Heart;
import com.common.dipping.api.board.repository.BoardRepository;
import com.common.dipping.api.board.repository.HeartRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    // 내가 좋아요를 눌렀는지 안눌렀는지 반환
    public boolean isMylike(Long userId, Board board) {
        User user = userRepository.findById(userId).orElse(null);

        boolean mylike = heartRepository.existsByUserIdAndBoardId(user,board);
        return mylike;
    }

    // 포스트번호의 좋아요 개수 가져오기
    public long getCountByBoardId(Board board) {
        return heartRepository.countByBoardId(board);
    }

    // 유저아이디와 포스트번호를 통해 좋아요가 눌러져있다면 삭제 없다면 등록
    public int setHeartByUserIdAndBoardId(Long userId, Long boardId){
        User user = userRepository.findById(userId).orElse(null);
        Board board = boardRepository.findById(boardId).orElse(null);

        boolean mylike = heartRepository.existsByUserIdAndBoardId(user,board);
        if(!mylike){
            Heart heart = Heart.builder()
                    .user(user)
                    .board(board)
                    .build();
            heartRepository.save(heart);
            return 1;
        }else {
            Heart heart = heartRepository.findByUserIdAndBoardId(user,board).orElse(null);
            if(heart != null){
                heartRepository.deleteById(heart.getId());
                return 0;
            }else {
                return 2;
            }
        }
    }

    // 포스트의 좋아요 누른 사람 목록
    public List<Heart> getListByBoardId(Long boardId){
        Board board = boardRepository.findById(boardId).orElse(null);
        List<Heart> hearts = heartRepository.findAllByBoardId(board);
        return hearts;
    }

}
