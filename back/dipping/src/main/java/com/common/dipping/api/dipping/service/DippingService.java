package com.common.dipping.api.dipping.service;


import com.common.dipping.api.board.domain.dto.ProfilePostDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
            Dipping parentDipping = dippingRepository.findById(dippingDto.getParentId()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 부모 디핑입니다. parentDipping="+dippingDto.getParentId()));
            Dipping newdipping = Dipping.builder()
                    .dippingTitle(dippingDto.getDippingTitle())
                    .dippingContent(dippingDto.getDippingContent())
                    .parentDipping(parentDipping)
                    .openDipping(dippingDto.getOpenDipping())
                    .user(user).build();
            return dippingRepository.save(newdipping);
        } else{ //자신이 부모디핑인 경우
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

    public Dipping edit(DippingDto dippingDto) {
        User user = userRepository.findById(dippingDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + dippingDto.getUserId()));
        Dipping dipping =  dippingRepository.findById(dippingDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 디핑이 없습니다. id=" + dippingDto.getId()));
        dipping.update(dippingDto.getDippingTitle(),dippingDto.getDippingContent(),dippingDto.getOpenDipping());
        return dippingRepository.save(dipping);
    }

    public void editSong(List<DippingSongDto> dippingSongDto, Dipping dipping) {
        dippingSongRepository.deleteAllByDipping(dipping);
        registerSong(dippingSongDto, dipping);
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

    public boolean deleteDipping(Long dippingId) {
        Dipping dipping = dippingRepository.findById(dippingId).orElseThrow(() -> new IllegalArgumentException("해당 디핑이 없습니다. id=" + dippingId));
        List<Dipping> childDipping = getChildByDippingId(dipping);
        for (Dipping child: childDipping ) {
            dippingRepository.deleteById(child.getId());
        }
        dippingRepository.deleteById(dippingId);
        return dippingRepository.existsById(dippingId);
    }

    public List<Dipping> getChildByDippingId(Dipping dipping) {
        List<Dipping> dippings = dippingRepository.findAllByParentDipping(dipping);
        return dippings;
    }

    public List<Dipping> getListByrecent(Long userId,int pagenum,String search) {
        int offset = (pagenum-1) * 10;
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
        Pageable pageable = PageRequest.of(pagenum-1,10, Sort.by(Sort.Direction.DESC, "id"));
        List<Dipping> dippings = new ArrayList<>();
        if(search.isEmpty()){
            dippings = dippingRepository.findAllWithPaginationByParentDippingNullAndUserNot(user,pageable);
        }else if(!search.isEmpty()){
            String s = search.replace(" ", "|");
            dippings = dippingRepository.findAllWithPaginationByParentDippingNullAndUserNotDippingTitleDippingContent(user.getId(),offset,s,s);
        }

        return dippings;
    }

    public List<Dipping> getTrendDippings(int pagenum,String search) {
        int offset = (pagenum-1) * 10;
        LocalDateTime week = LocalDateTime.now();
        List<Dipping> dippings = new ArrayList<>();
        if(search.isEmpty()){
            dippings = dippingRepository.findAllWithDippingHeartByCreatedAt(week.minusDays(7),offset);
        }else if(!search.isEmpty()) {
            String s = search.replace(" ", "|");
            dippings = dippingRepository.findAllWithDippingHeartByCreatedAtDippingTitleDippingContent(week.minusDays(7),offset,s,s);
        }

        return dippings;
    }

    public int getCountChild(Dipping dipping){
        int count = dippingRepository.findChildCountByDippingId(dipping.getId());
        return count;
    }

    public List<ProfilePostDto> getAllDippingByUserId(Long id) {
        List<Dipping> dippingList = dippingRepository.findAllWithUserId(id);
        List<ProfilePostDto> list = new ArrayList<>();
        for(Dipping dipping: dippingList){
            List<DippingSongDto> dippingSongList = getDippingSongAllById(dipping);
            if(dippingSongList.size()==0){//해당 디핑에 곡이 없는 경우 songImgUrl은 비움(front에서 자체 이미지 삽입)
                list.add(new ProfilePostDto(dipping.getId(),""));
            } else{
                String songImgUrl = dippingSongList.get(0).getSongImgUrl();
                list.add(new ProfilePostDto(dipping.getId(), songImgUrl));
            }
        }
        return list;
    }

    public List<Dipping> getFollowingDippings(Long id,int pagenum,String search) {
        int offset = (pagenum-1) * 10;
        List<Dipping> dippings = new ArrayList<>();
        if(search.isEmpty()){
            dippings = dippingRepository.findAllWithFollowingUser(id,offset);
        }else if(!search.isEmpty()) {
            String s = search.replace(" ", "|");
            dippings = dippingRepository.findAllWithFollowingUserDippingTitleDippingContent(id,offset,s,s);
        }

        return dippings;
    }
}
