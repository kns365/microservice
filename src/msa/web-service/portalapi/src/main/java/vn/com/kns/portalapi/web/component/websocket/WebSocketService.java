package vn.com.kns.portalapi.web.component.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private static String topicChat = "/topic/chat";
    private static SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public static void sendMessageToTopic(String topic, String message) {
        messagingTemplate.convertAndSend(topic, message);
    }

    public static void sendNotification(String message) {
        messagingTemplate.convertAndSend(topicChat, message);
    }
}
