package com.kns.apps.microservice.authservice.application.service.role;

import com.kns.apps.microservice.authservice.application.service.role.dto.RoleDto;
import com.kns.apps.microservice.authservice.application.service.role.dto.RoleInputDto;
import com.kns.apps.microservice.authservice.core.entity.Privilege;
import com.kns.apps.microservice.authservice.core.entity.Role;
import com.kns.apps.microservice.authservice.data.repository.PrivilegeRepository;
import com.kns.apps.microservice.authservice.data.repository.RoleRepository;
import com.kns.apps.microservice.authservice.data.specification.RoleSpec;
import com.kns.apps.microservice.configserver.core.model.FilterInput;
import com.kns.apps.microservice.configserver.core.model.PagingInput;
import com.kns.apps.microservice.configserver.core.model.PagingOutput;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "roles")
    @Override
    public List<RoleDto> getAll() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().forEach(list::add);
        List<RoleDto> output = list.stream()
                .map(source -> modelMapper.map(source, RoleDto.class))
                .collect(Collectors.toList());
        for (RoleDto item : output) {
            item.setPrivilegesString(item.getPrivileges().stream().map(p -> p.getName()).collect(Collectors.toList()));
        }
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<Role> spec = RoleSpec.filterBy(f);
        Page<Role> page = roleRepository.findAll(spec, pageable);

        List<RoleDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, RoleDto.class))
                .collect(Collectors.toList());

        for (RoleDto item : content) {
            item.setPrivilegesString(item.getPrivileges().stream().map(p -> p.getName()).collect(Collectors.toList()));
        }

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public RoleDto getById(Long id) {
        Optional<Role> data = roleRepository.findById(id);
        if (data.isPresent()) {
            RoleDto output = modelMapper.map(data.get(), RoleDto.class);
            output.setPrivilegesString(data.get().getPrivileges().stream().map(p -> p.getName()).collect(Collectors.toList()));
            return output;
        }
        return null;
    }

    @CacheEvict(value = "roles", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @CacheEvict(value = "roles", allEntries = true)
    @Override
    @Transactional
    public RoleDto createOrEdit(RoleInputDto input) {
        RoleDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private List<Privilege> getPrivilegesByPrivilegesString(List<String> privilegesString, List<Privilege> privileges) {
        List<Privilege> output = new ArrayList<>();
        for (String privilegeName : privilegesString) {
            Optional<Privilege> existPrivilege = privileges.stream().filter(p -> p.getName().equals(privilegeName)).findFirst();
            if (existPrivilege.isPresent()) {
                output.add(existPrivilege.get());
            } else {
                Optional<Privilege> privilege = privilegeRepository.findByName(privilegeName);
                if (privilege.isPresent()) {
                    output.add(privilege.get());
                }
            }
        }
        return output;
    }

    private RoleDto create(RoleInputDto input) {
        Role data = modelMapper.map(input, Role.class);
        data.setPrivileges(getPrivilegesByPrivilegesString(input.getPrivilegesString(), new ArrayList<>()));
        return modelMapper.map(roleRepository.save(data), RoleDto.class);
    }

    private RoleDto edit(RoleInputDto input) {
        Role data = roleRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        data.setPrivileges(getPrivilegesByPrivilegesString(input.getPrivilegesString(), new ArrayList<>()));
        return modelMapper.map(data, RoleDto.class);
    }
}
