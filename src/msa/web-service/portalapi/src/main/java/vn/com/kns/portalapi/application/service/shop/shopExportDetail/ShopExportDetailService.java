package vn.com.kns.portalapi.application.service.shop.shopExportDetail;

import vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto.ShopExportDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto.ShopExportDetailInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ShopExportDetailService {

    List<ShopExportDetailDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    ShopExportDetailDto getById(Long id);

    void deleteById(Long id);

    ShopExportDetailDto createOrEdit(ShopExportDetailInputDto input);

}
