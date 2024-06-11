package vn.com.kns.portalapi.application.service.category.province;

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
import vn.com.kns.portalapi.application.service.category.province.dto.ProvinceDto;
import vn.com.kns.portalapi.application.service.category.province.dto.ProvinceInputDto;
import vn.com.kns.portalapi.core.entity.other.Province;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.ProvinceRepository;
import vn.com.kns.portalapi.data.specification.other.ProvinceSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "provinces")
    @Override
    public List<ProvinceDto> getAll(Long countryId) {
        List<Province> list = new ArrayList<>();
        if (countryId != null) {
            provinceRepository.findAllByCountryId(countryId).forEach(list::add);
        } else {
            provinceRepository.findAll().forEach(list::add);
        }
        List<ProvinceDto> output = list.stream()
                .map(source -> modelMapper.map(source, ProvinceDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Province> spec = ProvinceSpec.filterBy(f);
        Page<Province> page = provinceRepository.findAll(spec, pageable);

        List<ProvinceDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, ProvinceDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public ProvinceDto getById(Long id) {
        Optional<Province> data = provinceRepository.findById(id);
        if (data.isPresent()) {
            ProvinceDto output = modelMapper.map(data.get(), ProvinceDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "provinces", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        provinceRepository.deleteById(id);
    }

    @CacheEvict(value = "provinces", allEntries = true)
    @Override
    @Transactional
    public ProvinceDto createOrEdit(ProvinceInputDto input) {
        ProvinceDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private ProvinceDto create(ProvinceInputDto input) {
        Province data = modelMapper.map(input, Province.class);
        return modelMapper.map(provinceRepository.save(data), ProvinceDto.class);
    }

    private ProvinceDto edit(ProvinceInputDto input) {
        Province data = provinceRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, ProvinceDto.class);
    }
}
