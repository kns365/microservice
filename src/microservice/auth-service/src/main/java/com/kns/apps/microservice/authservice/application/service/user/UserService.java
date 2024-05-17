package com.kns.apps.microservice.authservice.application.service.user;

import com.kns.apps.microservice.authservice.application.service.user.dto.UserDto;
import com.kns.apps.microservice.authservice.application.service.user.dto.UserInputDto;
import com.kns.apps.microservice.configserver.core.model.PagingInput;
import com.kns.apps.microservice.configserver.core.model.PagingOutput;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    PagingOutput getAllPaging(PagingInput input);

    UserDto getById(Long id);

    void deleteById(Long id);

    UserDto createOrEdit(UserInputDto input);

}
