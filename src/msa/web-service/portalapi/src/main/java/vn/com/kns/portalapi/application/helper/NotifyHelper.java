package vn.com.kns.portalapi.application.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.com.kns.portalapi.application.service.notify.NotifyService;
import vn.com.kns.portalapi.application.service.notify.dto.NotifyInputDto;
import vn.com.kns.portalapi.web.component.websocket.WebSocketService;

@Slf4j
@Component
public class NotifyHelper {

    private static NotifyService notifyService;

    public NotifyHelper(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    public static void sendNotify(String actionName, String serviceTargetId, String serviceTargetName, String template) {
        try {
            NotifyInputDto notify = new NotifyInputDto(AuthHelper.getCurrentUsername(), actionName, serviceTargetId, serviceTargetName, template);
            WebSocketService.sendNotification(notify.getData());
            log.debug("{}", notify);
            notifyService.createOrEdit(notify);
        } catch (Exception e) {
            log.error("Error create notify");
        }
    }

    public static void sendNotify(String serviceSourceId, String serviceSourceName, String actionName, String actionCount, String serviceTargetId, String serviceTargetName, String template) {
        try {
            NotifyInputDto notify = new NotifyInputDto(serviceSourceId, serviceSourceName, actionName, actionCount, serviceTargetId, serviceTargetName, AuthHelper.getCurrentUsername(), template);
            WebSocketService.sendNotification(notify.getData());
            log.debug("{}", notify);
            notifyService.createOrEdit(notify);
        } catch (Exception e) {
            log.error("Error create notify");
        }
    }

}
