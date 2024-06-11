package vn.com.kns.portalapi.application.service.category.item;

import vn.com.kns.portalapi.application.service.category.item.dto.ItemDto;
import vn.com.kns.portalapi.application.service.category.item.dto.ItemInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAll(Long groupItemId);

    PagingOutput getAllPaging(PagingInput input);

    ItemDto getById(Long id);

    void deleteById(Long id);

    ItemDto createOrEdit(ItemInputDto input);

}
