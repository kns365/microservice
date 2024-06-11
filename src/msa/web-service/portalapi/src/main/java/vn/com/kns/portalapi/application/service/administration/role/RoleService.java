package vn.com.kns.portalapi.application.service.administration.role;

import vn.com.kns.portalapi.application.service.administration.role.dto.RoleDto;
import vn.com.kns.portalapi.application.service.administration.role.dto.RoleInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    RoleDto getById(Long id);

    void deleteById(Long id);

    RoleDto createOrEdit(RoleInputDto input);

}
