package vn.com.kns.portalapi.application.service.administration.privilege;

import vn.com.kns.portalapi.application.service.administration.privilege.dto.PrivilegeDto;
import vn.com.kns.portalapi.application.service.administration.privilege.dto.PrivilegeInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface PrivilegeService {

    List<PrivilegeDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    PrivilegeDto getById(Long id);

    void deleteById(Long id);

    PrivilegeDto createOrEdit(PrivilegeInputDto input);

}
