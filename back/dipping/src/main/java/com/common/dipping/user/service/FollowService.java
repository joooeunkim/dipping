package com.common.dipping.user.service;

import com.common.dipping.user.domain.entity.Follow;
import com.common.dipping.user.domain.entity.User;
import com.common.dipping.user.repository.FollowRepository;
import com.common.dipping.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
