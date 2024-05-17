package com.kns.apps.microservice.authservice.application.service.privilege;

import com.kns.apps.microservice.authservice.application.service.privilege.dto.PrivilegeDto;
import com.kns.apps.microservice.authservice.application.service.privilege.dto.PrivilegeInputDto;
import com.kns.apps.microservice.configserver.core.model.PagingInput;
import com.kns.apps.microservice.configserver.core.model.PagingOutput;

import java.util.List;

public interface PrivilegeService {

    List<PrivilegeDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    PrivilegeDto getById(Long id);

    void deleteById(Long id);

    PrivilegeDto createOrEdit(PrivilegeInputDto input);

}
