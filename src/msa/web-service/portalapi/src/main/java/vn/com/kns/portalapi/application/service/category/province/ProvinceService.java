package vn.com.kns.portalapi.application.service.category.province;

import vn.com.kns.portalapi.application.service.category.province.dto.ProvinceDto;
import vn.com.kns.portalapi.application.service.category.province.dto.ProvinceInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface ProvinceService {

    List<ProvinceDto> getAll(Long countryId);

    PagingOutput getAllPaging(PagingInput input);

    ProvinceDto getById(Long id);

    void deleteById(Long id);

    ProvinceDto createOrEdit(ProvinceInputDto input);

}
