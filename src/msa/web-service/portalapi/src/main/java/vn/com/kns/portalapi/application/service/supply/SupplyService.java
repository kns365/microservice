package vn.com.kns.portalapi.application.service.supply;

import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface SupplyService {

    List<SupplyDto> getAll(Long supplierId);

    PagingOutput getAllPaging(PagingInput input);

    SupplyDto getById(Long id);

    SupplyDto getByItemCode(String input);

    void deleteById(Long id);

    SupplyDto createOrEdit(SupplyInputDto input);


}
