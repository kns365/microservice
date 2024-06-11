package vn.com.kns.portalapi.web.controller.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.shop.shopExportDetail.ShopExportDetailService;
import vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto.ShopExportDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopExportDetail.dto.ShopExportDetailInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shopExportDetails")
public class ShopExportDetailController {

    @Autowired
    private ShopExportDetailService shopExportDetailService;

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORTDETAIL)
    @GetMapping("/")
    public ResponseEntity<?> getAllShopExportDetail() {
        ResponseDto response = new ResponseDto();
        try {
            List<ShopExportDetailDto> output = shopExportDetailService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopExportDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORTDETAIL)
    @PostMapping("/getAllShopExportDetailPaging")
    public ResponseEntity<?> getAllShopExportDetailPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = shopExportDetailService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ShopExportDetailDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopExportDetailPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORTDETAIL)
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopExportDetailById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopExportDetailDto output = shopExportDetailService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getShopExportDetailById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORTDETAIL_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createShopExportDetail(@RequestBody ShopExportDetailInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopExportDetailDto shopExportDetail = shopExportDetailService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, shopExportDetail.getId());
        } catch (Exception e) {
            log.error("Error createShopExportDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORTDETAIL_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editShopExportDetail(@RequestBody ShopExportDetailInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            shopExportDetailService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editShopExportDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPEXPORTDETAIL_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopExportDetailById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            shopExportDetailService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteShopExportDetailById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
