package vn.com.kns.portalapi.application.service.notify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyInputDto {
    private Long id;
    // Shop              A                  đã  tạo         1  order             ABC
    //{serviceSourceId} {serviceSourceName} đã {actionName} 1 {serviceTargetId} {serviceTargetName}
    private String serviceSourceId;
    private String serviceSourceName;
    private String actionName;
    private String actionCount;
    private String serviceTargetId;
    private String serviceTargetName;
    //    private String notifyTemplate = "{serviceSourceId} {serviceSourceName} đã {actionName} {actionCount} {serviceTargetId} {serviceTargetName}";
    private String templateData = "{0} {1} {2} {3}";

    private String template;
    private String username;
    private String data;
    private Boolean state;

    public NotifyInputDto(String serviceSourceId, String serviceSourceName, String actionName, String actionCount, String serviceTargetId, String serviceTargetName
            , String username, String template) {
        this.data = MessageFormat.format(this.templateData, serviceSourceId, serviceSourceName, actionName, actionCount, serviceTargetId, serviceTargetName);
        this.username = username;
        this.template = template;
        this.state = false;
    }

    public NotifyInputDto(String username, String actionName, String serviceTargetId, String serviceTargetName, String template) {
        this.data = MessageFormat.format(this.templateData, username, actionName, serviceTargetId, serviceTargetName);
        this.username = username;
        this.template = template;
        this.state = false;
    }
}
