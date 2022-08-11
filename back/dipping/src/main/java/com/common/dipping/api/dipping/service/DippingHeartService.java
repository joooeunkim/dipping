package com.common.dipping.api.dipping.service;

import com.common.dipping.api.board.domain.dto.HeartDto;
import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.Heart;
import com.common.dipping.api.dipping.domain.dto.DippingHeartDto;
import com.common.dipping.api.dipping.domain.entity.Dipping;
import com.common.dipping.api.dipping.domain.entity.DippingHeart;
import com.common.dipping.api.dipping.repository.DippingHeartRepository;
import com.common.dipping.api.dipping.repository.DippingRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DippingHeartService {

    private final UserRepository userRepository;
    private final DippingRepository dippingRepository;
    private final DippingHeartRepository dippingHeartRepository;

    // 내가 좋아요를 눌렀는지 안눌렀는지 반환
    public boolean isMylikeByDippingId(Long userId, Dipping dipping) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id="+userId));

        boolean mylike = dippingHeartRepository.existsByUserAndDipping(user,dipping);
        return mylike;
    }

    // 포스트번호의 좋아요 개수 가져오기
    public int getCountByDippingId(Dipping dipping) {
        return dippingHeartRepository.findHeartsCountByDippingId(dipping.getId());
    }

    // 유저아이디와 포스트번호를 통해 좋아요가 눌러져있다면 삭제 없다면 등록
    public int setHeartByUserIdAndDippingId(Long userId, Long dippingId){
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. id="+userId));
        Dipping dipping = dippingRepository.findById(dippingId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+dippingId));

        boolean mylike = dippingHeartRepository.existsByUserAndDipping(user,dipping);
        if(!mylike){
            DippingHeart heart = DippingHeart.builder()
                    .user(user)
                    .dipping(dipping)
                    .build();
            dippingHeartRepository.save(heart);
            return 1; // 좋아요 누름
        }else {
            Optional<DippingHeart> heartopt = dippingHeartRepository.findByUserAndDipping(user,dipping);
            if(!heartopt.isEmpty()){
                DippingHeart dippingHeart = heartopt.get();
                dippingHeartRepository.deleteById(dippingHeart.getId());
                return 0; // 좋아요 삭제
            }else {
                return 2; // 오류 발생
            }
        }
    }

    public List<DippingHeartDto> getListByDippingId(Long dippingId){
        Dipping dipping = dippingRepository.findById(dippingId).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+dippingId));
        List<DippingHeart> dippingHearts = dippingHeartRepository.findAllByDipping(dipping).orElse(new ArrayList<>());

        List<DippingHeartDto> dippingHeartDtos = new ArrayList<>();
        if(!dippingHearts.isEmpty()){

            for (DippingHeart h: dippingHearts) {
                DippingHeartDto dippingHeartDto = new DippingHeartDto();
                dippingHeartDto.setId(h.getId());
                dippingHeartDto.setDippingId(h.getDipping().getId());
                dippingHeartDto.setUserId(h.getUser().getId());

                dippingHeartDtos.add(dippingHeartDto);
            }
        }

        return dippingHeartDtos;
    }
}
