package com.kns.apps.msa.authservice.application.service.app.user;

import com.kns.apps.msa.authservice.application.service.app.user.dto.UserDto;
import com.kns.apps.msa.authservice.application.service.app.user.dto.UserInputDto;
import com.kns.apps.msa.commonpack.core.model.PagingInput;
import com.kns.apps.msa.commonpack.core.model.PagingOutput;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    UserDto getById(Long id);

    void deleteById(Long id);

    UserDto createOrEdit(UserInputDto input);

}
