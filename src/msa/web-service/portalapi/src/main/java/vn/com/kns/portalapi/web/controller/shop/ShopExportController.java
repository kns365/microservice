package vn.com.kns.portalapi.web.controller.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.shop.shopExport.ShopExportService;
import vn.com.kns.portalapi.application.service.shop.shopExport.dto.ShopExportDto;
import vn.com.kns.portalapi.application.service.shop.shopExport.dto.ShopExportInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shopExports")
public class ShopExportController {

    @Autowired
    private ShopExportService shopExportService;

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORT)
    @GetMapping("/")
    public ResponseEntity<?> getAllShopExport() {
        ResponseDto response = new ResponseDto();
        try {
            List<ShopExportDto> output = shopExportService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopExport {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORT)
    @PostMapping("/getAllShopExportPaging")
    public ResponseEntity<?> getAllShopExportPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = shopExportService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ShopExportDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopExportPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORT)
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopExportById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopExportDto output = shopExportService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getShopExportById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORT_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createShopExport(@RequestBody ShopExportInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopExportDto shopExport = shopExportService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, shopExport.getId());
        } catch (Exception e) {
            log.error("Error createShopExport {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORT_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editShopExport(@RequestBody ShopExportInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            shopExportService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editShopExport {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORT_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopExportById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            shopExportService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteShopExportById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
