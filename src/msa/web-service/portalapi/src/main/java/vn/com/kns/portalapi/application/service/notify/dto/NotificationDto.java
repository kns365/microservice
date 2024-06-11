package vn.com.kns.portalapi.application.service.notify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.kns.portalapi.core.model.BaseEntityDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long unreadCount;
    private List<NotifyDto> notifies;

    public NotificationDto(List<NotifyDto> input) {
        this.notifies = input;
        this.unreadCount = input.stream().filter(p -> p.getState() == false).count();
    }
}
