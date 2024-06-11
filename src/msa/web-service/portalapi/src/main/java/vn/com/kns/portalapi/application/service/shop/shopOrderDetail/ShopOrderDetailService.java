package vn.com.kns.portalapi.application.service.shop.shopOrderDetail;

import vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto.ShopOrderDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto.ShopOrderDetailInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ShopOrderDetailService {

    List<ShopOrderDetailDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    ShopOrderDetailDto getById(Long id);

    void deleteById(Long id);

    ShopOrderDetailDto createOrEdit(ShopOrderDetailInputDto input);

}
