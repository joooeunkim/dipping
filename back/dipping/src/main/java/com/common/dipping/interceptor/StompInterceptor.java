package com.common.dipping.interceptor;

import com.common.dipping.api.chat.domain.dto.ChatMessage;
import com.common.dipping.api.chat.domain.dto.ChatUser;
import com.common.dipping.api.chat.repository.ChatRoomRepository;
import com.common.dipping.api.chat.service.ChatService;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Websocket 연결 시 요청 header의 jwt token 유효성을 검증하는 코드를 다음과 같이 추가
 * 유효하지 않을 경우 예외 처리
 *
 * 퇴장, 입장 안내 메시지는 공통적인 부분이므로 클라이언트가 메시지를 보내기보단 서버에서 일괄적으로 처리
 * 입장시 이벤트 : StompCommand.SUBSCRIBE ; 인원수를 +1 갱신하여 캐시에 저장. 정보(sessionId와 roomId)를 조합하여 캐시를 남김.
 * 퇴장시 이벤트 : StompCommand.DISCONNECT ; 캐시에 저장된 정보로 채팅방 정보를 얻어, 인원수를 -1 갱신하여 캐시에 저장
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT == accessor.getCommand()) { // websocket 연결요청
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            System.out.println("StompInterceptor-preSend0-jwtToken:"+jwtToken);
            // 회원일 경우, Header의 jwt token 검증
            if (jwtToken != null) {
                // 회원 token 받음
                jwtProvider.isValidToken(jwtToken);
            } else {
                // 비회원 : id, password 받음
            }
        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 채팅룸 구독요청
            System.out.println("구독요청");
            // header정보에서 구독 destination정보를 얻고, roomId를 추출한다.
            String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            // 채팅방의 인원수를 +1한다.
            chatRoomRepository.plusUserCount(roomId);
            // 클라이언트 입장 메시지를 채팅방에 발송한다.(redis publish)
            // OAuth가 이것저것 principal 건드리고 다녀서 user_id가 출력됨. 미가입자나 익명의 유저 처리 고민 (강민구)
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            String name = "익명의 유저";
            String img = null;
            String userId = null;
            if (jwtToken != null) {
                // 회원일 경우 이름 변경
                User user = jwtProvider.getUser(jwtToken);
                name = user.getNickname();
                img = user.getProfileImgUrl();
                userId = String.valueOf(user.getId());
            }
            // 채팅방에 들어온 클라이언트 정보를 roomId와 맵핑해 놓는다.(나중에 특정 세션이 어떤 채팅방에 들어가 있는지 알기 위함)
            chatRoomRepository.setUserEnterInfo(sessionId, ChatUser.builder().sender(name).roomId(roomId).userId(userId).build());
            chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.ENTER).roomId(roomId).sender(name).img(img).build());
            log.info("SUBSCRIBED {}, {}", name, roomId);
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료
            // 연결이 종료된 클라이언트 sesssionId로 채팅방 id를 얻는다.
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            // 퇴장한 클라이언트의 sessionId로 roomId를 얻고, roomId 맵핑 정보를 삭제한다.
            ChatUser chatUser = chatRoomRepository.getUserEnterRoomId(sessionId);
            String name = chatUser.getSender();
            String roomId = chatUser.getRoomId().replaceFirst("playroom-", "");

            chatRoomRepository.removeUserEnterInfo(sessionId);
            // 채팅방의 인원수를 -1한다.
            chatRoomRepository.minusUserCount(roomId);
            // 클라이언트 퇴장 메시지를 채팅방에 발송한다.(redis publish)
            chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.QUIT).roomId(roomId).sender(name).build());
            log.info("DISCONNECTED {}, {}", sessionId, roomId);
        }
        return message;
    }
}
