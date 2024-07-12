package com.kns.apps.msa.authservice.web.controller.app;

import com.kns.apps.msa.authservice.application.service.app.privilege.PrivilegeService;
import com.kns.apps.msa.authservice.application.service.app.privilege.dto.PrivilegeDto;
import com.kns.apps.msa.authservice.application.service.app.privilege.dto.PrivilegeInputDto;
import com.kns.apps.msa.commonpack.controller.BaseController;
import com.kns.apps.msa.commonpack.core.constant.HasPrivilege;
import com.kns.apps.msa.commonpack.core.model.PagingInput;
import com.kns.apps.msa.commonpack.core.model.PagingOutput;
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import com.kns.apps.msa.commonpack.core.model.dataTables.DataTablesInput;
import com.kns.apps.msa.commonpack.core.model.dataTables.DataTablesOutput;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/privileges")
public class PrivilegeController extends BaseController {

    @Autowired
    private PrivilegeService privilegeService;

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.PRIVILEGE)
    @GetMapping("/")
    public ResponseEntity<?> getAllPrivilege() {
        ResponseDto response = new ResponseDto();
        try {
            List<PrivilegeDto> output = privilegeService.getAll();
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllPrivilege {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.PRIVILEGE)
    @PostMapping("/getAllPrivilegePaging")
    public ResponseEntity<ResponseDto> getAllPrivilegePaging(@RequestBody DataTablesInput input, HttpServletRequest req) throws Exception {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = privilegeService.getAllPaging(new PagingInput(input));
            DataTablesOutput<PrivilegeDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllPrivilegePaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.PRIVILEGE)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getPrivilegeById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            PrivilegeDto output = privilegeService.getById(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getPrivilegeById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.PRIVILEGE_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createPrivilege(@RequestBody PrivilegeInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            if (BooleanUtils.isTrue(input.getIsTemplate())) {
                String name = input.getName();
                Arrays.stream(new String[]{"", "_CREATE", "_EDIT", "_DELETE"}).forEach(template -> {
                    input.setName(name + template);
                    privilegeService.createOrEdit(input);
                });
            } else {
                privilegeService.createOrEdit(input);
            }
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error createPrivilege {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.PRIVILEGE_EDIT)
    @PutMapping("/")
    public ResponseEntity<ResponseDto> editPrivilege(@RequestBody PrivilegeInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            privilegeService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editPrivilege {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @HystrixCommand(defaultFallback = "defaultFallback")
    @PreAuthorize(HasPrivilege.PRIVILEGE_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deletePrivilegeById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            privilegeService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deletePrivilegeById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null, null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
