package vn.com.kns.portalapi.web.controller.app;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.administration.user.UserService;
import vn.com.kns.portalapi.application.service.administration.user.dto.UserDto;
import vn.com.kns.portalapi.application.service.administration.user.dto.UserInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize(HasPrivilegeConst.USER)
    @GetMapping("/")
    public ResponseEntity<?> getAllUser() {
        ResponseDto response = new ResponseDto();
        try {
            List<UserDto> output = userService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllUser {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilegeConst.USER)
    @PostMapping("/getAllUserPaging")
    public ResponseEntity<ResponseDto> getAllUserPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = userService.getAllPaging(new PagingInput(input));
            DataTablesOutput<UserDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllUserPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> defaultFallback() {
        log.info("fallbackGetAllUserPaging ... ");
        com.kns.apps.msa.commonpack.core.model.ResponseDto res = new com.kns.apps.msa.commonpack.core.model.ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.USER)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            UserDto output = userService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getUserById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.USER_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            userService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error createUser {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.USER_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editUser(@RequestBody UserInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            userService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editUser {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.USER_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUserById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            userService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteUserById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
