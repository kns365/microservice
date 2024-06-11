package vn.com.kns.portalapi.web.component.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    static SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/ws/send-message")
    public void sendMessageToTopic(@RequestBody MessagePayload payload) {
        webSocketService.sendMessageToTopic(payload.getTopic(), payload.getMessage());
    }

    //----------------------------------------------------------------------------------------------

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    //----------------------------------------------------------------------------------------------

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
    }

    //----------------------------------------------------------------------------------------------

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public String chat(String msg) {
        System.out.println(msg);
        return msg;
    }

    @MessageMapping("/private")
    public void chatToUser(String msg) {
        System.out.println(msg);
        this.simpMessagingTemplate.convertAndSendToUser("user","/specific","");
    }


}
