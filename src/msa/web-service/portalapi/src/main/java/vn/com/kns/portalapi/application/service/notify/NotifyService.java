package vn.com.kns.portalapi.application.service.notify;

import vn.com.kns.portalapi.application.service.notify.dto.NotifyDto;
import vn.com.kns.portalapi.application.service.notify.dto.NotifyInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface NotifyService {

    List<NotifyDto> getAll(String username, Integer max);

    PagingOutput getAllPaging(PagingInput input);

    NotifyDto getById(Long id);
    NotifyDto readNotifyById(Long id);

    void deleteById(Long id);

    NotifyDto createOrEdit(NotifyInputDto input);

    NotifyDto sendNotify(NotifyInputDto input);


}
