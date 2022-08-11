package com.common.dipping.api.chat.service;

import com.common.dipping.api.chat.domain.dto.ChatMessage;
import com.common.dipping.api.chat.domain.dto.ChatRoom;
import com.common.dipping.api.chat.domain.dto.ChatUserList;
import com.common.dipping.api.chat.repository.ChatRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 채팅 메시지 발송 일원화
 */
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;


    // 나의 전체 채팅방 목록 조회
    public List<ChatRoom> findAllRoomOfUser(String username) {

        List<ChatRoom> chatRooms = chatRepository.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRepository.getUserCount(room.getRoomId())));

        List<ChatRoom> myChatRooms = new LinkedList<>();
        for (ChatRoom room: chatRooms) {
            if(room.getName().contains(username)){
                myChatRooms.add(room);
            }
        }
        return myChatRooms;
    }

    // 새로운 채팅을 시작할 수 있는 사람 목록 조회
    public List<ChatUserList> findAllNewUser(String username) {
        List<ChatUserList> newUser = new LinkedList<>();
        List<ChatRoom> myChatRooms = findAllRoomOfUser(username);
        List<User> allUser = userRepository.findAll();
        for (User user: allUser) {
            boolean isNew = true;
            for(ChatRoom chatRoom: myChatRooms){
                if(chatRoom.getName().contains(user.getEmail())){
                    isNew = false;
                }
            }
            if(isNew){
                newUser.add(new ChatUserList(user.getEmail(), user.getNickname(), user.getProfileImgUrl()));
            }
        }
        return newUser;
    }

    //새로운 채팅방 생성하기
    public ChatRoom createChatRoom(String name) {
        return chatRepository.createChatRoom(name);
    }

    /**
     * destination정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage chatMessage) {

        chatMessage.setUserCount(chatRepository.getUserCount(chatMessage.getRoomId()));
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setSender("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("[알림]");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }



}