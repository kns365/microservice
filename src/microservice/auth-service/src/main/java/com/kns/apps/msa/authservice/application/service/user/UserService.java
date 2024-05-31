package com.kns.apps.msa.authservice.application.service.user;

import com.kns.apps.msa.authservice.application.service.user.dto.UserDto;
import com.kns.apps.msa.authservice.application.service.user.dto.UserInputDto;
import com.kns.apps.msa.configservice.core.model.PagingInput;
import com.kns.apps.msa.configservice.core.model.PagingOutput;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    UserDto getById(Long id);

    void deleteById(Long id);

    UserDto createOrEdit(UserInputDto input);

}
