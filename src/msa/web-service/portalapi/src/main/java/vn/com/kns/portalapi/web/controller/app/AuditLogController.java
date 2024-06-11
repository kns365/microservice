package vn.com.kns.portalapi.web.controller.app;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.helper.AuthHelper;
import vn.com.kns.portalapi.application.service.administration.auditLog.AuditLogService;
import vn.com.kns.portalapi.application.service.administration.auditLog.dto.AuditLogDto;
import vn.com.kns.portalapi.application.service.administration.auditLog.dto.AuditLogInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/auditLogs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PreAuthorize(HasPrivilegeConst.AUDITLOG)
    @Hidden
    @GetMapping("/")
    public ResponseEntity<?> getAllAuditLog() {
        ResponseDto response = new ResponseDto();
        try {
            List<AuditLogDto> output = auditLogService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllAuditLog {}", e);
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.AUDITLOG)
    @PostMapping("/getAllAuditLogPaging")
    public ResponseEntity<?> getAllAuditLogPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = auditLogService.getAllPaging(new PagingInput(input));
            DataTablesOutput<AuditLogDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllAuditLogPaging {}", e);
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.AUDITLOG)
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuditLogById(@PathVariable("id") Long id) {
        ResponseDto response = new ResponseDto();
        try {
            AuditLogDto output = auditLogService.getById(id);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAuditLogById {}", e);
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.AUDITLOG_CREATE)
    @Hidden
    @PostMapping("/")
    public ResponseEntity<?> createAuditLog(@RequestBody AuditLogInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            auditLogService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error createAuditLog {}", e);
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.AUDITLOG_EDIT)
    @Hidden
    @PutMapping("/")
    public ResponseEntity<?> editAuditLog(@RequestBody AuditLogInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            auditLogService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editAuditLog {}", e);
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.AUDITLOG_DELETE)
    @Hidden
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuditLogById(@PathVariable("id") Long id) {
        ResponseDto response = new ResponseDto();
        try {
            auditLogService.deleteById(id);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteAuditLogById {}", e);
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
