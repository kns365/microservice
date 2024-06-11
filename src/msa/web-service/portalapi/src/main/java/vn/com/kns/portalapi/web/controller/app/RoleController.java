package vn.com.kns.portalapi.web.controller.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.administration.role.RoleService;
import vn.com.kns.portalapi.application.service.administration.role.dto.RoleDto;
import vn.com.kns.portalapi.application.service.administration.role.dto.RoleInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize(HasPrivilegeConst.ROLE)
    @GetMapping("/")
    public ResponseEntity<?> getAllRole() {
        ResponseDto response = new ResponseDto();
        try {
            List<RoleDto> output = roleService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllRole {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE)
    @PostMapping("/getAllRolePaging")
    public ResponseEntity<?> getAllRolePaging(@RequestBody DataTablesInput input, HttpServletRequest request) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = roleService.getAllPaging(new PagingInput(input));
            DataTablesOutput<RoleDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllRolePaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getRoleById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            RoleDto output = roleService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getRoleById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE_CREATE)
    @PostMapping("/")
    public ResponseEntity<ResponseDto> createRole(@RequestBody RoleInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            roleService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error createRole {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE_EDIT)
    @PutMapping("/")
    public ResponseEntity<ResponseDto> editRole(@RequestBody RoleInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            roleService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editRole {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteRoleById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            roleService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteRoleById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
