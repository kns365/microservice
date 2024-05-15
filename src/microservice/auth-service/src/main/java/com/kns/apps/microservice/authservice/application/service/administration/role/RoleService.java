package com.kns.apps.microservice.authservice.application.service.administration.role;

import com.kns.apps.microservice.authservice.application.service.administration.role.dto.RoleDto;
import com.kns.apps.microservice.authservice.application.service.administration.role.dto.RoleInputDto;
import com.kns.apps.microservice.configserver.core.model.PagingInput;
import com.kns.apps.microservice.configserver.core.model.PagingOutput;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    RoleDto getById(Long id);

    void deleteById(Long id);

    RoleDto createOrEdit(RoleInputDto input);

}
