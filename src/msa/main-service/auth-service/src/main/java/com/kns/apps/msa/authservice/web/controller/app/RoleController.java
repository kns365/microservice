package com.kns.apps.msa.authservice.web.controller.app;

import com.kns.apps.msa.authservice.application.service.app.role.RoleService;
import com.kns.apps.msa.authservice.application.service.app.role.dto.RoleDto;
import com.kns.apps.msa.authservice.application.service.app.role.dto.RoleInputDto;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
    @RequestMapping("/api/roles")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.ROLE)
    @GetMapping("/")
    public ResponseEntity<?> getAllRole() {
        ResponseDto response = new ResponseDto();
        try {
            List<RoleDto> output = roleService.getAll();
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllRole {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.ROLE)
    @PostMapping("/getAllRolePaging")
    public ResponseEntity<?> getAllRolePaging(@RequestBody DataTablesInput input, HttpServletRequest request) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = roleService.getAllPaging(new PagingInput(input));
            DataTablesOutput<RoleDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllRolePaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.ROLE)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getRoleById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            RoleDto output = roleService.getById(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getRoleById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.ROLE_CREATE)
    @PostMapping("/")
    public ResponseEntity<ResponseDto> createRole(@RequestBody RoleInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            roleService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error createRole {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.ROLE_EDIT)
    @PutMapping("/")
    public ResponseEntity<ResponseDto> editRole(@RequestBody RoleInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            roleService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editRole {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.ROLE_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteRoleById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            roleService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deleteRoleById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
