package vn.com.kns.portalapi.application.service.shop.shopImport;

import vn.com.kns.portalapi.application.service.shop.shopImport.dto.ShopImportDto;
import vn.com.kns.portalapi.application.service.shop.shopImport.dto.ShopImportInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ShopImportService {

    List<ShopImportDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    ShopImportDto getById(Long id);

    void deleteById(Long id);

    ShopImportDto createOrEdit(ShopImportInputDto input);

}
