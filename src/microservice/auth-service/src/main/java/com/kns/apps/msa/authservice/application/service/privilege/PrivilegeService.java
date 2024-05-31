package com.kns.apps.msa.authservice.application.service.privilege;

import com.kns.apps.msa.authservice.application.service.privilege.dto.PrivilegeDto;
import com.kns.apps.msa.authservice.application.service.privilege.dto.PrivilegeInputDto;
import com.kns.apps.msa.configservice.core.model.PagingInput;
import com.kns.apps.msa.configservice.core.model.PagingOutput;

import java.util.List;

public interface PrivilegeService {

    List<PrivilegeDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    PrivilegeDto getById(Long id);

    void deleteById(Long id);

    PrivilegeDto createOrEdit(PrivilegeInputDto input);

}
