package vn.com.kns.portalapi.application.service.category.country;

import vn.com.kns.portalapi.application.service.category.country.dto.CountryDto;
import vn.com.kns.portalapi.application.service.category.country.dto.CountryInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface CountryService {

    List<CountryDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    CountryDto getById(Long id);

    void deleteById(Long id);

    CountryDto createOrEdit(CountryInputDto input);

}
