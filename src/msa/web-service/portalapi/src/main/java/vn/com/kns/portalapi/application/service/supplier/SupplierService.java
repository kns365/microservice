package vn.com.kns.portalapi.application.service.supplier;

import vn.com.kns.portalapi.application.service.supplier.dto.SupplierDto;
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface SupplierService {

    List<SupplierDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    SupplierDto getById(Long id);

    void deleteById(Long id);

    SupplierDto createOrEdit(SupplierInputDto input);

    SupplierDto getByCode(String input);
}
