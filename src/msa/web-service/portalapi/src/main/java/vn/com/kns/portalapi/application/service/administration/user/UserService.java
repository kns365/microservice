package vn.com.kns.portalapi.application.service.administration.user;

import vn.com.kns.portalapi.application.service.administration.user.dto.UserDto;
import vn.com.kns.portalapi.application.service.administration.user.dto.UserInputDto;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    UserDto getById(Long id);

    void deleteById(Long id);

    UserDto createOrEdit(UserInputDto input);

}
