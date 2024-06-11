package vn.com.kns.portalapi.application.service.shop.shop;

import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopDto;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ShopService {

    List<ShopDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    ShopDto getById(Long id);

    void deleteById(Long id);

    ShopDto createOrEdit(ShopInputDto input);

    ShopDto getByCode(String input);

}
