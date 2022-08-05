package com.common.dipping.api.user.service;

import java.util.List;

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
        User sender = userRepository.findByUserNickname(senderNickname).orElse(null);
        User receiver = userRepository.findByUserNickname(receiverNickname).orElse(null);
        if (sender == null || receiver == null) {
            return -2;
        }
        Follow follow = followRepository.findBySenderAndReceiver(sender, receiver).orElse(null);

        if (follow != null) {
            followRepository.deleteById(follow.getId());
            return follow.getId();
        }else {
            Follow newFollow = Follow.builder().fromUser(sender).toUser(receiver).build();
            followRepository.save(newFollow);
            return -1;
        }
    }
    
    public List<Follow> getfollowListByFromUser(long fromUser){
    	User user = userRepository.findById(fromUser).orElse(null);
    	List<Follow> list = followRepository.findAllBySender(user);
    	return list;
    }

    public List<Follow> getFollowListByFromUserNickname(String fromUser){
        User user = userRepository.findByUserNickname(fromUser).orElse(null);
        List<Follow> list = followRepository.findAllBySender(user);
        return list;
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
