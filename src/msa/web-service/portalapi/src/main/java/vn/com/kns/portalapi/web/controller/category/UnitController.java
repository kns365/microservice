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
import vn.com.kns.portalapi.core.model.ResponseDto;
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
        ResponseDto response = new ResponseDto();
        try {
            List<UnitDto> output  = unitService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllUnit {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT)
    @PostMapping("/getAllUnitPaging")
    public ResponseEntity<?> getAllUnitPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = unitService.getAllPaging(new PagingInput(input));
            DataTablesOutput<UnitDto> output  = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllUnitPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            UnitDto output  = unitService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getUnitById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createUnit(@RequestBody UnitInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            unitService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error createUnit {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editUnit(@RequestBody UnitInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            unitService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editUnit {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.UNIT_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUnitById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            unitService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteUnitById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
