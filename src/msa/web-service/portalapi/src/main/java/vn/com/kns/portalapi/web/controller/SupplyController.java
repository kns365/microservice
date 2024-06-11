package vn.com.kns.portalapi.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopDto;
import vn.com.kns.portalapi.application.service.supply.SupplyService;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @PreAuthorize(HasPrivilegeConst.SUPPLY)
    @GetMapping("/")
    public ResponseEntity<?> getAllSupply() {
        ResponseDto response = new ResponseDto();
        try {
            List<SupplyDto> output = supplyService.getAll(null);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllSupply {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLY)
    @GetMapping("/getAllSupplyBySupplierId/{supplierId}")
    public ResponseEntity<?> getAllSupplyBySupplierId(@PathVariable("supplierId") Long supplierId) {
        ResponseDto response = new ResponseDto();
        try {
            List<SupplyDto> output = supplyService.getAll(supplierId);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllSupplyBySupplierId {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLY)
    @PostMapping("/getAllSupplyPaging")
    public ResponseEntity<?> getAllSupplyPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = supplyService.getAllPaging(new PagingInput(input));
            DataTablesOutput<SupplyDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllSupplyPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLY)
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplyById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            SupplyDto output = supplyService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getSupplyById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLY)
    @GetMapping("")
    public ResponseEntity<?> getSupplyByItemCode(@RequestParam(value = "itemCode", required = false) String itemCode) {
        ResponseDto response = new ResponseDto();
        try {
            SupplyDto output = supplyService.getByItemCode(itemCode);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getSupplyByItemCode {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLY_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createSupply(@RequestBody SupplyInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            SupplyDto supply = supplyService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, supply.getId());
        } catch (Exception e) {
            log.error("Error createSupply {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLY_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editSupply(@RequestBody SupplyInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            supplyService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editSupply {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SUPPLY_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplyById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            supplyService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteSupplyById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
