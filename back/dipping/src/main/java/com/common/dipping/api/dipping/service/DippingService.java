package com.common.dipping.api.dipping.service;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.BoardSong;
import com.common.dipping.api.dipping.domain.dto.DippingDto;
import com.common.dipping.api.dipping.domain.dto.DippingSongDto;
import com.common.dipping.api.dipping.domain.entity.Dipping;
import com.common.dipping.api.dipping.domain.entity.DippingSong;
import com.common.dipping.api.dipping.repository.DippingHeartRepository;
import com.common.dipping.api.dipping.repository.DippingRepository;
import com.common.dipping.api.dipping.repository.DippingSongRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DippingService {

    private final UserRepository userRepository;
    private final DippingRepository dippingRepository;
    private final DippingHeartRepository dippingHeartRepository;
    private final DippingSongRepository dippingSongRepository;

    public Dipping getDippingOne(Long dippingId){
        Dipping dipping = dippingRepository.findById(dippingId).orElseThrow(() -> new IllegalArgumentException("해당 딥핑이 없습니다. id=" + dippingId));
        return dipping;
    }

    public Dipping register(DippingDto dippingDto) {
        User user = userRepository.findById(dippingDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + dippingDto.getUserId()));

        //부모 디핑이 있는 경우
        if(dippingDto.getParentId()!=null){
            System.out.println("no parent");
            Dipping parentDipping = dippingRepository.findById(dippingDto.getParentId()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 부모 디핑입니다. parentDipping="+dippingDto.getParentId()));
            Dipping newdipping = Dipping.builder()
                    .dippingTitle(dippingDto.getDippingTitle())
                    .dippingContent(dippingDto.getDippingContent())
                    .parentDipping(parentDipping)
                    .openDipping(dippingDto.getOpenDipping())
                    .user(user).build();
            return dippingRepository.save(newdipping);
        } else{ //자신이 부모디핑인 경우
            System.out.println("i'm parent");
            Dipping newdipping = Dipping.builder()
                    .dippingTitle(dippingDto.getDippingTitle())
                    .dippingContent(dippingDto.getDippingContent())
                    .openDipping(dippingDto.getOpenDipping())
                    .user(user).build();

            return dippingRepository.save(newdipping);
        }

    }

    public void registerSong(List<DippingSongDto> dippingSongDto, Dipping newDipping) {
        if (!dippingSongDto.isEmpty()) {
            for (int i = 0; i < dippingSongDto.size(); i++) {
                if (dippingSongDto.get(i) != null) {
                    DippingSong dippingSong = DippingSong.builder()
                            .songTitle(dippingSongDto.get(i).getSongTitle())
                            .songSinger(dippingSongDto.get(i).getSongSinger())
                            .songUrl(dippingSongDto.get(i).getSongUrl())
                            .songImgUrl(dippingSongDto.get(i).getSongImgUrl())
                            .dipping(newDipping).build();
                    dippingSongRepository.save(dippingSong);
                }
            }
        }
    }

    public List<DippingSongDto> getDippingSongAllById(Dipping dipping) {
        List<DippingSong> list = dippingRepository.findDippingByDippingId(dipping.getId());
        List<DippingSongDto> dippingSongDtos = new ArrayList<>();
        if(!list.isEmpty()) {
            for (DippingSong ds : list) {
                DippingSongDto dippingSongDto = new DippingSongDto(ds);
                dippingSongDtos.add(dippingSongDto);
            }
        }
        return dippingSongDtos;
    }

    public List<Dipping> getChildByDippingId(Dipping dipping) {
        List<Dipping> dippings = dippingRepository.findAllByParentDipping(dipping);
        return dippings;
    }

    public List<Dipping> getListByDippingId() {
        List<Dipping> dippings = dippingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return dippings;
    }


}
