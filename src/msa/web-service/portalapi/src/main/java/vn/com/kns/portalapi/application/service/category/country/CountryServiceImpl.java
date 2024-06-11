package vn.com.kns.portalapi.application.service.category.country;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.kns.portalapi.application.service.category.country.dto.CountryDto;
import vn.com.kns.portalapi.application.service.category.country.dto.CountryInputDto;
import vn.com.kns.portalapi.core.entity.other.Country;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.CountryRepository;
import vn.com.kns.portalapi.data.specification.other.CountrySpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "countries")
    @Override
    public List<CountryDto> getAll() {
        List<Country> list = new ArrayList<>();
        countryRepository.findAll().forEach(list::add);
        List<CountryDto> output = list.stream()
                .map(source -> modelMapper.map(source, CountryDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Country> spec = CountrySpec.filterBy(f);
        Page<Country> page = countryRepository.findAll(spec, pageable);

        List<CountryDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, CountryDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public CountryDto getById(Long id) {
        Optional<Country> data = countryRepository.findById(id);
        if (data.isPresent()) {
            CountryDto output = modelMapper.map(data.get(), CountryDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "countries", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }

    @CacheEvict(value = "countries", allEntries = true)
    @Override
    @Transactional
    public CountryDto createOrEdit(CountryInputDto input) {
        CountryDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private CountryDto create(CountryInputDto input) {
        Country data = modelMapper.map(input, Country.class);
        return modelMapper.map(countryRepository.save(data), CountryDto.class);
    }

    private CountryDto edit(CountryInputDto input) {
        Country data = countryRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, CountryDto.class);
    }
}
