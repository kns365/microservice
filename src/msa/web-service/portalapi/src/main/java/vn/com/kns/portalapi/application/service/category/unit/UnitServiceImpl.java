package vn.com.kns.portalapi.application.service.category.unit;

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
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitDto;
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitInputDto;
import vn.com.kns.portalapi.core.entity.other.Unit;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.UnitRepository;
import vn.com.kns.portalapi.data.specification.other.UnitSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "units")
    @Override
    public List<UnitDto> getAll() {
        List<Unit> list = new ArrayList<>();
        unitRepository.findAll().forEach(list::add);
        List<UnitDto> output = list.stream()
                .map(source -> modelMapper.map(source, UnitDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Unit> spec = UnitSpec.filterBy(f);
        Page<Unit> page = unitRepository.findAll(spec, pageable);

        List<UnitDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, UnitDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public UnitDto getById(Long id) {
        Optional<Unit> data = unitRepository.findById(id);
        if (data.isPresent()) {
            UnitDto output = modelMapper.map(data.get(), UnitDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "units", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        unitRepository.deleteById(id);
    }

    @CacheEvict(value = "units", allEntries = true)
    @Override
    @Transactional
    public UnitDto createOrEdit(UnitInputDto input) {
        UnitDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private UnitDto create(UnitInputDto input) {
        Unit data = modelMapper.map(input, Unit.class);
        return modelMapper.map(unitRepository.save(data), UnitDto.class);
    }

    private UnitDto edit(UnitInputDto input) {
        Unit data = unitRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, UnitDto.class);
    }

    public Unit createUnit(Unit input) {
        Unit output = unitRepository.save(input);
        return output;
    }

    public UnitDto createUnit2(UnitInputDto input) {
        Unit data = modelMapper.map(input, Unit.class);
        return modelMapper.map(unitRepository.save(data), UnitDto.class);
    }
}
