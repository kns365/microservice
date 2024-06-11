package vn.com.kns.portalapi.application.service.category.groupItem;

import vn.com.kns.portalapi.application.service.category.groupItem.dto.GroupItemDto;
import vn.com.kns.portalapi.application.service.category.groupItem.dto.GroupItemInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface GroupItemService {

    List<GroupItemDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    GroupItemDto getById(Long id);

    void deleteById(Long id);

    GroupItemDto createOrEdit(GroupItemInputDto input);

}
