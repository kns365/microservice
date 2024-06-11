package vn.com.kns.portalapi.application.service.category.district;

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
import vn.com.kns.portalapi.application.service.category.district.dto.DistrictDto;
import vn.com.kns.portalapi.application.service.category.district.dto.DistrictInputDto;
import vn.com.kns.portalapi.core.entity.other.District;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.DistrictRepository;
import vn.com.kns.portalapi.data.specification.other.DistrictSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "districts")
    @Override
    public List<DistrictDto> getAll(Long provinceId) {
        List<District> list = new ArrayList<>();
        if (provinceId != null) {
            districtRepository.findAllByProvinceId(provinceId).forEach(list::add);
        } else {
            districtRepository.findAll().forEach(list::add);
        }
        List<DistrictDto> output = list.stream()
                .map(source -> modelMapper.map(source, DistrictDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<District> spec = DistrictSpec.filterBy(f);
        Page<District> page = districtRepository.findAll(spec, pageable);

        List<DistrictDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, DistrictDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public DistrictDto getById(Long id) {
        Optional<District> data = districtRepository.findById(id);
        if (data.isPresent()) {
            DistrictDto output = modelMapper.map(data.get(), DistrictDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "districts", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        districtRepository.deleteById(id);
    }

    @CacheEvict(value = "districts", allEntries = true)
    @Override
    @Transactional
    public DistrictDto createOrEdit(DistrictInputDto input) {
        DistrictDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private DistrictDto create(DistrictInputDto input) {
        District data = modelMapper.map(input, District.class);
        return modelMapper.map(districtRepository.save(data), DistrictDto.class);
    }

    private DistrictDto edit(DistrictInputDto input) {
        District data = districtRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, DistrictDto.class);
    }
}
