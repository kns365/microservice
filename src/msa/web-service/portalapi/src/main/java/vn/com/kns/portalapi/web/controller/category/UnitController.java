package vn.com.kns.portalapi.web.controller.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.category.unit.UnitService;
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitDto;
import vn.com.kns.portalapi.application.service.category.unit.dto.UnitInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.util.Date;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @PreAuthorize(HasPrivilegeConst.UNIT)
    @GetMapping("/")
    public ResponseEntity<?> getAllUnit() {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<UnitDto> output  = unitService.getAll();
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllUnit {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT)
    @PostMapping("/getAllUnitPaging")
    public ResponseEntity<?> getAllUnitPaging(@RequestBody DataTablesInput input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            PagingOutput paging = unitService.getAllPaging(new PagingInput(input));
            DataTablesOutput<UnitDto> output  = new DataTablesOutput(input, paging);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllUnitPaging {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            UnitDto output  = unitService.getById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getUnitById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createUnit(@RequestBody UnitInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            unitService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error createUnit {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editUnit(@RequestBody UnitInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            unitService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editUnit {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUnitById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            unitService.deleteById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deleteUnitById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

}
