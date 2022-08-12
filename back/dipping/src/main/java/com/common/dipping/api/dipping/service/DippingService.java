package com.common.dipping.api.dipping.service;

import com.common.dipping.api.board.domain.entity.Board;
import com.common.dipping.api.board.domain.entity.BoardSong;
import com.common.dipping.api.dipping.domain.dto.DippingSongDto;
import com.common.dipping.api.dipping.domain.entity.Dipping;
import com.common.dipping.api.dipping.domain.entity.DippingSong;
import com.common.dipping.api.dipping.repository.DippingHeartRepository;
import com.common.dipping.api.dipping.repository.DippingRepository;
import com.common.dipping.api.dipping.repository.DippingSongRepository;
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
