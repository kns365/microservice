package com.kns.apps.msa.authservice.application.service.user;

import com.kns.apps.msa.authservice.application.service.user.dto.UserDto;
import com.kns.apps.msa.authservice.application.service.user.dto.UserInputDto;
import com.kns.apps.msa.authservice.core.entity.Role;
import com.kns.apps.msa.authservice.core.entity.User;
import com.kns.apps.msa.authservice.data.repository.RoleRepository;
import com.kns.apps.msa.authservice.data.repository.UserRepository;
import com.kns.apps.msa.authservice.data.specification.UserSpec;
import com.kns.apps.msa.configservice.core.model.FilterInput;
import com.kns.apps.msa.configservice.core.model.PagingInput;
import com.kns.apps.msa.configservice.core.model.PagingOutput;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Cacheable(value = "users")
    @Override
    public List<UserDto> getAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().forEach(list::add);
        List<UserDto> output = list.stream()
                .map(source -> modelMapper.map(source, UserDto.class))
                .collect(Collectors.toList());
        for (UserDto item : output) {
            item.setRolesString(item.getRoles().stream().map(p -> p.getName()).collect(Collectors.toList()));
        }
        return output;
    }

    @Override
    public PagingOutput getAllPaging(PagingInput input) {
        FilterInput f = input.getFilter();

        Sort sort = input.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(input.getSortBy()).ascending() : Sort.by(input.getSortBy()).descending();
        Pageable pageable = PageRequest.of(input.getPageNo(), input.getPageSize(), sort);
        Specification<User> spec = UserSpec.filterBy(f);
        Page<User> page = userRepository.findAll(spec, pageable);

        List<UserDto> content = page.getContent().stream()
                .map(source -> modelMapper.map(source, UserDto.class))
                .collect(Collectors.toList());

        for (UserDto item : content) {
            item.setRolesString(item.getRoles().stream().map(p -> p.getName()).collect(Collectors.toList()));
        }

        PagingOutput paging = new PagingOutput(page.getNumber(), page.getSize(), page.getNumberOfElements(), page.getTotalElements(), page.getTotalPages(), page.isLast(), content);
        return paging;
    }

    @Override
    public UserDto getById(Long id) {
        Optional<User> data = userRepository.findById(id);
        if (data.isPresent()) {
            UserDto output = modelMapper.map(data.get(), UserDto.class);
            output.setRolesString(data.get().getRoles().stream().map(p -> p.getName()).collect(Collectors.toList()));
            return output;
        }
        return null;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    @Transactional
    public UserDto createOrEdit(UserInputDto input) {
        UserDto output;
        if (input.getId() == null) {
            output = create(input);
        } else {
            output = edit(input);
        }
        return output;
    }

    private List<Role> getRolesByRolesString(List<String> rolesString, List<Role> roles) {
        List<Role> output = new ArrayList<>();
        for (String roleName : rolesString) {
            Optional<Role> existRole = roles.stream().filter(p -> p.getName().equals(roleName)).findFirst();
            if (existRole.isPresent()) {
                output.add(existRole.get());
            } else {
                Optional<Role> role = roleRepository.findByName(roleName);
                if (role.isPresent()) {
                    output.add(role.get());
                }
            }
        }
        return output;
    }

    private UserDto create(UserInputDto input) {
        User data = modelMapper.map(input, User.class);
        data.setRoles(getRolesByRolesString(input.getRolesString(), new ArrayList<>()));
        return modelMapper.map(userRepository.save(data), UserDto.class);
    }

    private UserDto edit(UserInputDto input) {
        User data = userRepository.findById(input.getId()).get();
        modelMapper.map(input, data);
        data.setRoles(getRolesByRolesString(input.getRolesString(), data.getRoles()));
        return modelMapper.map(data, UserDto.class);
    }
}
