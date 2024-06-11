package vn.com.kns.portalapi.application.service.shop.shopExport;

import vn.com.kns.portalapi.application.service.shop.shopExport.dto.ShopExportDto;
import vn.com.kns.portalapi.application.service.shop.shopExport.dto.ShopExportInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ShopExportService {

    List<ShopExportDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    ShopExportDto getById(Long id);

    void deleteById(Long id);

    ShopExportDto createOrEdit(ShopExportInputDto input);

}
