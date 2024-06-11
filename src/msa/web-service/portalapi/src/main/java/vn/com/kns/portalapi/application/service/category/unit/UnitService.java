package vn.com.kns.portalapi.application.service.category.unit;

import vn.com.kns.portalapi.application.service.category.unit.dto.UnitDto;
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitInputDto;
import vn.com.kns.portalapi.core.entity.other.Unit;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface UnitService {

    List<UnitDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    UnitDto getById(Long id);

    void deleteById(Long id);

    UnitDto createOrEdit(UnitInputDto input);

    Unit createUnit(Unit input);
    UnitDto createUnit2(UnitInputDto input);

}
