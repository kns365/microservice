package vn.com.kns.portalapi.application.service.administration.privilege;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.kns.portalapi.application.service.administration.privilege.dto.PrivilegeDto;
import vn.com.kns.portalapi.application.service.administration.privilege.dto.PrivilegeInputDto;
import vn.com.kns.portalapi.core.constant.HasRoleConst;
import vn.com.kns.portalapi.core.entity.app.Privilege;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.data.repository.app.PrivilegeRepository;
import vn.com.kns.portalapi.data.specification.app.PrivilegeSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "privileges")
    @Override
    public List<PrivilegeDto> getAll() {
        List<Privilege> list = new ArrayList<>();
        privilegeRepository.findAll().forEach(list::add);
        List<PrivilegeDto> output = list.stream()
                .map(source -> modelMapper.map(source, PrivilegeDto.class))
                .collect(Collectors.toList());
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Privilege> spec = PrivilegeSpec.filterBy(f);
        Page<Privilege> page = privilegeRepository.findAll(spec, pageable);

        List<PrivilegeDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, PrivilegeDto.class))
                .collect(Collectors.toList());

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public PrivilegeDto getById(Long id) {
        Optional<Privilege> data = privilegeRepository.findById(id);
        if (data.isPresent()) {
            return modelMapper.map(data.get(), PrivilegeDto.class);
        }
        return null;
    }

    @CacheEvict(value = "privileges", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        privilegeRepository.deleteById(id);
    }

    @CacheEvict(value = "privileges", allEntries = true)
    @Override
    @Transactional
    public PrivilegeDto createOrEdit(PrivilegeInputDto input) {
        PrivilegeDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private PrivilegeDto create(PrivilegeInputDto input) {
        Privilege data = modelMapper.map(input, Privilege.class);
        return modelMapper.map(privilegeRepository.save(data), PrivilegeDto.class);
    }

    private PrivilegeDto edit(PrivilegeInputDto input) {
        Privilege data = privilegeRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        return modelMapper.map(data, PrivilegeDto.class);
    }
}
