package vn.com.kns.portalapi.application.service.shop.shopImportDetail;

import vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto.ShopImportDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto.ShopImportDetailInputDto;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ShopImportDetailService {

    List<ShopImportDetailDto> getAll(Long shopId);

    PagingOutput getAllPaging(PagingInput input);

    ShopImportDetailDto getById(Long id);

    void deleteById(Long id);

    ShopImportDetailDto createOrEdit(ShopImportDetailInputDto input);

    List<SupplyDto> getAllSupplyByShopId(Long shopId);

}
