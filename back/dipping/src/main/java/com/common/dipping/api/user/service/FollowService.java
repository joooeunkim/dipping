package com.common.dipping.api.user.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.common.dipping.api.user.domain.dto.FollowerListDto;
import com.common.dipping.api.user.domain.dto.FollowingListDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.dipping.api.user.domain.entity.Follow;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.FollowRepository;
import com.common.dipping.api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public long getFollowIdByFromEmailToEmail(String senderNickname, String receiverNickname) {
        User sender = userRepository.findByNickname(senderNickname).orElse(null);
        User receiver = userRepository.findByNickname(receiverNickname).orElse(null);
        if (sender == null || receiver == null) {
            return -2;
        }
        Follow follow = followRepository.findBySenderAndReceiver(sender, receiver).orElse(null);

        if (follow != null) {
            followRepository.deleteById(follow.getId());
            return follow.getId();
        }else {
            Follow newFollow = Follow.builder().sender(sender).receiver(receiver).build();
            followRepository.save(newFollow);
            return -1;
        }
    }
    
    public List<Follow> getfollowListByFromUser(Long fromUser){
    	User user = userRepository.findById(fromUser).orElse(null);
    	List<Follow> list = followRepository.findAllBySender(user);
        if(list.isEmpty()){
            return new ArrayList<>();
        }
    	return list;
    }

    public List<FollowingListDto> getFollowListBySenderNickname(String senderNickname){
        User user = userRepository.findByNickname(senderNickname).orElse(null);
        List<Follow> list = followRepository.findAllBySender(user);
        List<FollowingListDto> followingList = new ArrayList<>();
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            FollowingListDto followingListDto = new FollowingListDto();
            followingListDto.setFollowId(list.get(i).getId());
            followingListDto.setReceiverId(list.get(i).getReceiver().getId());
            followingListDto.setFollowCreated(list.get(i).getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss")));
            followingListDto.setProfileImgUrl(list.get(i).getReceiver().getProfileImgUrl());
            followingListDto.setNickname(list.get(i).getReceiver().getNickname());
            followingList.add(i, followingListDto);
        }
        return followingList;
    }

    public List<FollowerListDto> getFollowListByReceiverNickname(String receiverNickname){
        User user = userRepository.findByNickname(receiverNickname).orElse(null);
        List<Follow> list = followRepository.findAllByReceiver(user);
        List<FollowerListDto> followerList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            FollowerListDto followerListDto = new FollowerListDto();
            followerListDto.setFollowId(list.get(i).getId());
            followerListDto.setSenderId(list.get(i).getSender().getId());
            followerListDto.setFollowCreated(list.get(i).getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss")));
            followerListDto.setProfileImgUrl(list.get(i).getSender().getProfileImgUrl());
            followerListDto.setNickname(list.get(i).getSender().getNickname());
            followerList.add(i, followerListDto);
        }
        return followerList;
    }

//    @Transactional
//    public Follow save(String fromUserEmail, String toUserEmail) {
//        User fromUser = userRepository.findByEmail(fromUserEmail).orElse(null);
//        User toUser = userRepository.findByEmail(toUserEmail).orElse(null);
//
//        Follow follow = Follow.builder().fromUser(fromUser).toUser(toUser).build();
//
//        return followRepository.save(follow);
//    }
//
//    @Transactional
//    public void unFollow(Long id) {
//        followRepository.deleteById(id);
//    }
}
