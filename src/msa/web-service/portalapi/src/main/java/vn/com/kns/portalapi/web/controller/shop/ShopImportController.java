package vn.com.kns.portalapi.web.controller.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.shop.shopImport.ShopImportService;
import vn.com.kns.portalapi.application.service.shop.shopImport.dto.ShopImportDto;
import vn.com.kns.portalapi.application.service.shop.shopImport.dto.ShopImportInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shopImports")
public class ShopImportController {

    @Autowired
    private ShopImportService shopImportService;

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORT)
    @GetMapping("/")
    public ResponseEntity<?> getAllShopImport() {
        ResponseDto response = new ResponseDto();
        try {
            List<ShopImportDto> output = shopImportService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopImport {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORT)
    @PostMapping("/getAllShopImportPaging")
    public ResponseEntity<?> getAllShopImportPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = shopImportService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ShopImportDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopImportPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORT)
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopImportById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopImportDto output = shopImportService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getShopImportById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORT_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createShopImport(@RequestBody ShopImportInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopImportDto shopImport = shopImportService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, shopImport.getId());
        } catch (Exception e) {
            log.error("Error createShopImport {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORT_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editShopImport(@RequestBody ShopImportInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            shopImportService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editShopImport {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORT_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopImportById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            shopImportService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteShopImportById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
