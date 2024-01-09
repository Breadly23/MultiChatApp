package com.brandon.chatsystem.config;

import com.brandon.chatsystem.chat.ChatMessage;
import com.brandon.chatsystem.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@RequiredArgsConstructor
@Component
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        try {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
            String username = (String) accessor.getSessionAttributes().get("username");

            log.info("User disconnected: {}", username);

            if (username != null) {
                var chatMessage = ChatMessage.builder()
                        .type(MessageType.LEAVE)
                        .sender(username)
                        .build();
                messageTemplate.convertAndSend("/topic/public", chatMessage);
            }
        } catch (Exception e) {
            log.error("Error handling WebSocket disconnect event", e);
        }
    }
}
