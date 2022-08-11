package com.common.dipping.api.chat.service;

import com.common.dipping.api.chat.domain.dto.ChatMessage;
import com.common.dipping.api.chat.domain.dto.ChatRoom;
import com.common.dipping.api.chat.domain.dto.ChatUserList;
import com.common.dipping.api.chat.repository.ChatRepository;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import com.common.dipping.exception.UserNotFoundException;
import com.common.dipping.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.ResponseEntity;
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
                room.setChatter(findChatter(room.getName(), username));
                myChatRooms.add(room);
            }
        }
        return myChatRooms;
    }

    // 새로운 채팅을 시작할 수 있는 사람 목록 조회
    public List<ChatUserList> findAllNewUser(String username) {
        List<ChatUserList> newUser = new LinkedList<>();
        List<ChatRoom> myChatRooms = findAllRoomOfUser(username);
        if(myChatRooms.size()==0) return newUser;
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

    public ChatRoom findRoomById(String roomId, String username) {
        ChatRoom chatRoom = chatRepository.findRoomById(roomId);
        chatRoom.setChatter(findChatter(chatRoom.getName(),username));
        return chatRoom;
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

    //roomName에서 채팅하는 상대방 닉네임 분리
    private String findChatter(String roomName, String username){
        System.out.println("roomName:"+roomName+", username:"+username);
        String[] names = roomName.split(",");
        String chatter = (names[0].equals(username))?names[1]:username;
        System.out.println("chatter:"+chatter);
        User user = userRepository.findByEmail(chatter).orElse(null);
        System.out.println(user.getNickname());
        return user.getNickname();
    }


    public ResponseEntity saveMessages(String roomId, ChatMessage message, UserDetailsImpl userInfo) {

        // 로그인 회원 정보로 대화명 설정
        //message.setSender(nickname);
        //message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        //message.setProfileImgUrl(user.getProfileImgUrl());
        //message.setUserId(user.getId());
        // Websocket에 발행된 메시지를 redis로 발행한다(publish). redisTemplate을 통해 바로 ChannelTopic으로 메시지를 발행
        //redisTemplate.convertAndSend(channelTopic.getTopic(), message);
        // 발행한 메시지 저장
        chatRepository.saveMessage(roomId, message);
    }
}