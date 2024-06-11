package vn.com.kns.portalapi.application.service.shop.shopOrder;

import vn.com.kns.portalapi.application.service.shop.shopOrder.dto.ShopOrderDto;
import vn.com.kns.portalapi.application.service.shop.shopOrder.dto.ShopOrderInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ShopOrderService {

    List<ShopOrderDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    ShopOrderDto getById(Long id);

    void deleteById(Long id);

    ShopOrderDto createOrEdit(ShopOrderInputDto input);

}
