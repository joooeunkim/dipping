package com.common.dipping.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.dipping.user.domain.entity.Follow;
import com.common.dipping.user.domain.entity.User;
import com.common.dipping.user.repository.FollowRepository;
import com.common.dipping.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public long getFollowIdByFromEmailToEmail(String fromUserEmail, String toUserEmail) {
        User fromUser = userRepository.findByEmail(fromUserEmail).orElse(null);
        User toUser = userRepository.findByEmail(toUserEmail).orElse(null);

        Follow follow = followRepository.findByFromUserAndToUser(fromUser, toUser).orElse(null);

        if (follow != null) {
            followRepository.deleteById(follow.getId());
            return follow.getId();
        }else {
            Follow newFollow = Follow.builder().fromUser(fromUser).toUser(toUser).build();
            followRepository.save(newFollow);
            return -1;
        }
    }
    
    public List<Follow> getfollowListByFromUser(long fromUser){
    	User user = userRepository.findById(fromUser).orElse(null);
    	List<Follow> list = followRepository.findAllByFromUser(user);
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
