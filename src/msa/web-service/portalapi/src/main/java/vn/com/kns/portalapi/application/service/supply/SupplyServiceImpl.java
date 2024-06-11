package vn.com.kns.portalapi.application.service.supply;

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
import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyInputDto;
import vn.com.kns.portalapi.core.entity.other.Supply;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.other.SupplyRepository;
import vn.com.kns.portalapi.data.specification.other.SupplySpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "supplies")
    @Override
    public List<SupplyDto> getAll(Long supplierId) {
        List<Supply> list = new ArrayList<>();
        if (supplierId == null) {
            supplyRepository.findAll().forEach(list::add);
        } else {
            supplyRepository.findAllBySupplierId(supplierId).forEach(list::add);
        }
        List<SupplyDto> output = list.stream()
                .map(source -> modelMapper.map(source, SupplyDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Supply> spec = SupplySpec.filterBy(f);
        Page<Supply> page = supplyRepository.findAll(spec, pageable);

        List<SupplyDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, SupplyDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public SupplyDto getById(Long id) {
        Optional<Supply> data = supplyRepository.findById(id);
        if (data.isPresent()) {
            SupplyDto output = modelMapper.map(data.get(), SupplyDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "supplies", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        supplyRepository.deleteById(id);
    }

    @Cacheable(value = "supply")
    @Override
    public SupplyDto getByItemCode(String input) {
        Optional<Supply> data = supplyRepository.findByItem_Code(input);
        if (data.isPresent()) {
            SupplyDto output = modelMapper.map(data.get(), SupplyDto.class);
            return output;
        }
        return null;
    }

    @CacheEvict(value = "supplies", allEntries = true)
    @Override
    @Transactional
    public SupplyDto createOrEdit(SupplyInputDto input) {
        SupplyDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private SupplyDto create(SupplyInputDto input) {
        Supply temp = modelMapper.map(input, Supply.class);
        Supply data = supplyRepository.save(temp);
        return modelMapper.map(data, SupplyDto.class);
    }

    private SupplyDto edit(SupplyInputDto input) {
        Supply data = supplyRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, SupplyDto.class);
    }


}
