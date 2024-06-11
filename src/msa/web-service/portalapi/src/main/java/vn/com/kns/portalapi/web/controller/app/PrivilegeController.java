package vn.com.kns.portalapi.web.controller.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.administration.privilege.PrivilegeService;
import vn.com.kns.portalapi.application.service.administration.privilege.dto.PrivilegeDto;
import vn.com.kns.portalapi.application.service.administration.privilege.dto.PrivilegeInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/privileges")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @PreAuthorize(HasPrivilegeConst.PRIVILEGE)
    @GetMapping("/")
    public ResponseEntity<?> getAllPrivilege() {
        ResponseDto response = new ResponseDto();
        try {
            List<PrivilegeDto> output = privilegeService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllPrivilege {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @PreAuthorize(HasPrivilegeConst.PRIVILEGE)
    @PostMapping("/getAllPrivilegePaging")
    public ResponseEntity<ResponseDto> getAllPrivilegePaging(@RequestBody DataTablesInput input, HttpServletRequest req) throws Exception {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = privilegeService.getAllPaging(new PagingInput(input));
            DataTablesOutput<PrivilegeDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllPrivilegePaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.PRIVILEGE)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getPrivilegeById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            PrivilegeDto output = privilegeService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getPrivilegeById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @PreAuthorize(HasPrivilegeConst.PRIVILEGE_CREATE)
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
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error createPrivilege {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.PRIVILEGE_EDIT)
    @PutMapping("/")
    public ResponseEntity<ResponseDto> editPrivilege(@RequestBody PrivilegeInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            privilegeService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editPrivilege {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.PRIVILEGE_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deletePrivilegeById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            privilegeService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deletePrivilegeById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
