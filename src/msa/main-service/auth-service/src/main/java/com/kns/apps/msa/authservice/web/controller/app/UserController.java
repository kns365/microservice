package com.kns.apps.msa.authservice.web.controller.app;

import com.kns.apps.msa.authservice.application.service.app.user.UserService;
import com.kns.apps.msa.authservice.application.service.app.user.dto.UserDto;
import com.kns.apps.msa.authservice.application.service.app.user.dto.UserInputDto;
import com.kns.apps.msa.configservice.controller.BaseController;
import com.kns.apps.msa.configservice.core.constant.HasPrivilege;
import com.kns.apps.msa.configservice.core.model.PagingInput;
import com.kns.apps.msa.configservice.core.model.PagingOutput;
import com.kns.apps.msa.configservice.core.model.ResponseDto;
import com.kns.apps.msa.configservice.core.model.dataTables.DataTablesInput;
import com.kns.apps.msa.configservice.core.model.dataTables.DataTablesOutput;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.USER)
    @GetMapping("/")
    public ResponseEntity<?> getAllUser() {
        ResponseDto response = new ResponseDto();
        try {
            List<UserDto> output = userService.getAll();
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllUser {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.USER)
    @PostMapping("/getAllUserPaging")
    public ResponseEntity<ResponseDto> getAllUserPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = userService.getAllPaging(new PagingInput(input));
            DataTablesOutput<UserDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllUserPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.USER)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            UserDto output = userService.getById(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getUserById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.USER_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            userService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error createUser {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.USER_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editUser(@RequestBody UserInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            userService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editUser {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.USER_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUserById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            userService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deleteUserById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
