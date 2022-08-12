package com.common.dipping.api.alarm.service;


import com.common.dipping.api.alarm.domain.dto.AlarmDto;
import com.common.dipping.api.alarm.domain.entity.Alarm;
import com.common.dipping.api.alarm.repository.AlarmRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import com.common.dipping.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AlarmService {

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    public List<AlarmDto> getAlarmByReceiver(UserDetailsImpl requestUser) {
        User receiver = userRepository.findByEmail(requestUser.getName()).orElse(null);
        List<Alarm> alarmList = alarmRepository.findAllByReceiver(receiver);
        List<AlarmDto> alarmDtoList = new ArrayList<>();
        for (Alarm alarm: alarmList) {
            alarm.read();
            AlarmDto alarmDto = new AlarmDto();
            alarmDto.setAlarmMessage(alarm.getAlarmMessage());
            alarmDto.setAlarmRead(alarm.getAlarmRead());
            alarmDto.setAlarmType(alarm.getAlarmType());
            alarmDto.setSenderNickname(alarm.getSender().getNickname());
            alarmDto.setSenderProfileImgUrl(alarm.getSender().getProfileImgUrl());
            alarmDto.setCreateAt(alarm.getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss")));
            alarmDtoList.add(alarmDto);
        }
        return alarmDtoList;

    }

    public Alarm alarm(User sender, User receiver, String alarmType) {
        if (alarmType.equals("follow") && alarmRepository.existsByAlarmTypeAndSenderAndReceiver(alarmType, sender, receiver)) {
            return null;
        }
        String alarmMessage = sender.getNickname() + "님이 팔로우하였습니다.";

        Alarm newAlarm = Alarm.builder()
                .sender(sender)
                .receiver(receiver)
                .alarmType(alarmType)
                .alarmMessage(alarmMessage)
                .alarmRead(false)
                .build();
        return alarmRepository.save(newAlarm);
        }

    public Alarm alarmBySenderIdAndReceiverIdAndAlarmType(Long senderId, Long receiverId, String alarmType) {
        if (senderId == receiverId) {
            return null;
        }

        User sender = userRepository.findById(senderId).orElse(null);
        User receiver = userRepository.findById(receiverId).orElse(null);
        String alarmMessage;

        if (alarmType.contains("Like") && alarmRepository.existsByAlarmTypeAndSenderAndReceiver(alarmType, sender, receiver)) {
            return null;
        }

        if (alarmType.contains("BoardLike")) {
            alarmMessage = sender.getNickname() + "님이 게시글을 좋아합니다.";
        } else if (alarmType.contains("CommentLike")) {
            alarmMessage = sender.getNickname() + "님이 댓글을 좋아합니다.";
        } else if (alarmType.equals("Comment")){
            alarmMessage = sender.getNickname() + "님이 게시글에 댓글을 남겼습니다.";
        } else {
            alarmMessage = sender.getNickname() + "님이 게시글에 대댓글을 남겼습니다.";
        }

        Alarm newAlarm = Alarm.builder()
                .sender(sender)
                .receiver(receiver)
                .alarmType(alarmType)
                .alarmMessage(alarmMessage)
                .alarmRead(false)
                .build();

        return alarmRepository.save(newAlarm);

    }

    public Long CountAlarm(Long userId) {
        User receiver = userRepository.findById(userId).orElse(null);
        return alarmRepository.countAllByReceiver(receiver);
    }
}
