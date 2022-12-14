package com.common.dipping.api.chat.repository;

import com.common.dipping.api.chat.domain.dto.ChatMessage;
import com.common.dipping.api.chat.domain.dto.ChatRoom;
import com.common.dipping.api.chat.domain.dto.ChatUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 채팅방과 관련된 데이터를 이 곳(한 군데)에서 처리
 */

@RequiredArgsConstructor
@Repository
@Transactional
public class ChatRepository {

    private final RedisTemplate redisTemplate;

    // Redis CacheKeys
    private static final String CHAT_ROOMS = "CHAT_ROOM"; // 채팅룸 저장
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    public static final String USER_COUNT = "USER_COUNT"; // 채팅룸에 입장한 클라이언트수 저장
    public static final String CHAT_LIST = "CHAT_LIST"; // 채팅 룸 메시지 내역(최신 50 저장)

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, ChatRoom> hashOpsChatRoom;  // 채팅방 ("CHAT_ROOM", 방 id, chatroom 객체)

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, ChatUser> hashOpsEnterInfo;  // 채팅방 유저 정보("ENTER_INFO", 세션 id, 방 id)

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;  // 채팅방 유저수

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, List<ChatMessage>> roomMessages; // 최근 메시지 저장용


    // 모든 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        return hashOpsChatRoom.values(CHAT_ROOMS);
    }

    // 특정 채팅방 조회
    public ChatRoom findRoomById(String id) {
        return hashOpsChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        hashOpsChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    // 채팅방 삭제 : redis hash에 저장된 채팅방 파괴
    public void deleteChatRoom(String roomId) {
        // 채팅방 정보 삭제
        hashOpsChatRoom.delete(CHAT_ROOMS, roomId);
        // 채팅방 메시지 삭제
        roomMessages.delete(CHAT_LIST, roomId);
        // 채팅방 카운트 삭제
        redisTemplate.delete(USER_COUNT + "_" + roomId);

        // hashOpsEnterInfo(채팅방 유저 정보)에서 채팅방 ID를 value으로 가지는 key를 삭제
        Map<String, ChatUser> entries = hashOpsEnterInfo.entries(ENTER_INFO);
        for (Map.Entry<String, ChatUser> stringStringEntry : entries.entrySet()) {
            if (stringStringEntry.getValue().getRoomId().equals(roomId)) {
                hashOpsEnterInfo.delete(ENTER_INFO, stringStringEntry.getKey());
            }
        }
    }

    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
    public void setUserEnterInfo(String sessionId, ChatUser chatUser) {
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, chatUser);
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public ChatUser getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 채팅방ID 삭제
    public void removeUserEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }


    // 메시지 저장
    public ChatMessage saveMessage(String roomId, ChatMessage message) {
        List<ChatMessage> messages = roomMessages.get(CHAT_LIST, roomId);
        if(messages == null) messages = new ArrayList<>();
        messages.add(message);
        roomMessages.put(CHAT_LIST, roomId, messages);
        return message;
    }

    // 저장 메시지 보내기
    public List<ChatMessage> getMessages(String roomId) {
        List<ChatMessage> messages = roomMessages.get(CHAT_LIST, roomId);
        if(messages == null) return new ArrayList<>();
        return messages;
    }
}