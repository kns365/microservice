package com.kns.apps.msa.authservice.application.service.app.role;

import com.kns.apps.msa.authservice.application.service.app.role.dto.RoleDto;
import com.kns.apps.msa.authservice.application.service.app.role.dto.RoleInputDto;
import com.kns.apps.msa.commonpack.core.model.PagingInput;
import com.kns.apps.msa.commonpack.core.model.PagingOutput;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    RoleDto getById(Long id);

    void deleteById(Long id);

    RoleDto createOrEdit(RoleInputDto input);

}
