package vn.com.kns.portalapi.application.service.category.district;

import vn.com.kns.portalapi.application.service.category.district.dto.DistrictDto;
import vn.com.kns.portalapi.application.service.category.district.dto.DistrictInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface DistrictService {

    List<DistrictDto> getAll(Long provinceId);

    PagingOutput getAllPaging(PagingInput input);

    DistrictDto getById(Long id);

    void deleteById(Long id);

    DistrictDto createOrEdit(DistrictInputDto input);

}
