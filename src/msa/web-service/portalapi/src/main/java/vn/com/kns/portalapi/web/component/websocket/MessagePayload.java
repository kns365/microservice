package vn.com.kns.portalapi.web.component.websocket;

import lombok.Data;

@Data
public class MessagePayload {
    private String topic;
    private String message;
}
