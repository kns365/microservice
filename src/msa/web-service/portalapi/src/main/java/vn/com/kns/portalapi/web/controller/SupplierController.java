package vn.com.kns.portalapi.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.supplier.SupplierService;
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierDto;
import vn.com.kns.portalapi.application.service.supplier.dto.SupplierInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PreAuthorize(HasPrivilegeConst.SUPPLIER)
    @GetMapping("/")
    public ResponseEntity<?> getAllSupplier() {
        ResponseDto response = new ResponseDto();
        try {
            List<SupplierDto> output = supplierService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllSupplier {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLIER)
    @PostMapping("/getAllSupplierPaging")
    public ResponseEntity<?> getAllSupplierPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = supplierService.getAllPaging(new PagingInput(input));
            DataTablesOutput<SupplierDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllSupplierPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLIER)
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            SupplierDto output = supplierService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getSupplierById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLIER)
    @GetMapping("")
    public ResponseEntity<?> getSupplierByCode(@RequestParam(value = "code", required = false) String code) {
        ResponseDto response = new ResponseDto();
        try {
            SupplierDto output = supplierService.getByCode(code);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getSupplierByCode {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLIER_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createSupplier(@RequestBody SupplierInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            SupplierDto supplier = supplierService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, supplier.getId());
        } catch (Exception e) {
            log.error("Error createSupplier {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLIER_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editSupplier(@RequestBody SupplierInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            supplierService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editSupplier {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLIER_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplierById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            supplierService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteSupplierById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }


}
