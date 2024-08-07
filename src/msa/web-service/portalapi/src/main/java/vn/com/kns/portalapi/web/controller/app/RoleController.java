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
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kns.apps.msa.commonpack.application.helper.LogHelper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.util.Date;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private RoleService roleService;

    @PreAuthorize(HasPrivilegeConst.ROLE)
    @GetMapping("/")
    public ResponseEntity<?> getAllRole() {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<RoleDto> output = roleService.getAll();
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllRole {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, null, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE)
    @PostMapping("/getAllRolePaging")
    public ResponseEntity<?> getAllRolePaging(@RequestBody DataTablesInput input, HttpServletRequest request) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            PagingOutput paging = roleService.getAllPaging(new PagingInput(input));
            DataTablesOutput<RoleDto> output = new DataTablesOutput(input, paging);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllRolePaging {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getRoleById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            RoleDto output = roleService.getById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getRoleById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE_CREATE)
    @PostMapping("/")
    public ResponseEntity<ResponseDto> createRole(@RequestBody RoleInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            roleService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error createRole {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE_EDIT)
    @PutMapping("/")
    public ResponseEntity<ResponseDto> editRole(@RequestBody RoleInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            roleService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editRole {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ROLE_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteRoleById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            roleService.deleteById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deleteRoleById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

}
