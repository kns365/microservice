package vn.com.kns.portalapi.web.controller.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.shop.shopImportDetail.ShopImportDetailService;
import vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto.ShopImportDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopImportDetail.dto.ShopImportDetailInputDto;
import vn.com.kns.portalapi.application.service.supply.dto.SupplyDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shopImportDetails")
public class ShopImportDetailController {

    @Autowired
    private ShopImportDetailService shopImportDetailService;

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORTDETAIL)
    @GetMapping("/")
    public ResponseEntity<?> getAllShopImportDetail() {
        ResponseDto response = new ResponseDto();
        try {
            List<ShopImportDetailDto> output = shopImportDetailService.getAll(null);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopImportDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORTDETAIL)
    @GetMapping("/getAllShopImportDetailByShopId/{shopId}")
    public ResponseEntity<?> getAllShopImportDetailByShopId(@PathVariable("shopId") Long shopId) {
        ResponseDto response = new ResponseDto();
        try {
            List<ShopImportDetailDto> output = shopImportDetailService.getAll(shopId);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopImportDetailByShopId {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORTDETAIL)
    @GetMapping("/getAllSupplylByShopId/{shopId}")
    public ResponseEntity<?> getAllSupplylByShopId(@PathVariable("shopId") Long shopId) {
        ResponseDto response = new ResponseDto();
        try {
//            List<ShopImportDetailDto> output = shopImportDetailService.getAllSupplyByShopId(shopId);
            List<SupplyDto> output = shopImportDetailService.getAllSupplyByShopId(shopId);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllSupplylByShopId {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORTDETAIL)
    @PostMapping("/getAllShopImportDetailPaging")
    public ResponseEntity<?> getAllShopImportDetailPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = shopImportDetailService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ShopImportDetailDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopImportDetailPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORTDETAIL)
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopImportDetailById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopImportDetailDto output = shopImportDetailService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getShopImportDetailById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORTDETAIL_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createShopImportDetail(@RequestBody ShopImportDetailInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopImportDetailDto shopImportDetail = shopImportDetailService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, shopImportDetail.getId());
        } catch (Exception e) {
            log.error("Error createShopImportDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORTDETAIL_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editShopImportDetail(@RequestBody ShopImportDetailInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            shopImportDetailService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editShopImportDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPIMPORTDETAIL_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopImportDetailById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            shopImportDetailService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteShopImportDetailById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
