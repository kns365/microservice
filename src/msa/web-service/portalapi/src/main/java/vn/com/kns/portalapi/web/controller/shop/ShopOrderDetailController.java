package vn.com.kns.portalapi.web.controller.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.shop.shopOrderDetail.ShopOrderDetailService;
import vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto.ShopOrderDetailDto;
import vn.com.kns.portalapi.application.service.shop.shopOrderDetail.dto.ShopOrderDetailInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shopOrderDetails")
public class ShopOrderDetailController {

    @Autowired
    private ShopOrderDetailService shopOrderDetailService;

    @PreAuthorize(HasPrivilegeConst.SHOPORDERDETAIL)
    @GetMapping("/")
    public ResponseEntity<?> getAllShopOrderDetail() {
        ResponseDto response = new ResponseDto();
        try {
            List<ShopOrderDetailDto> output = shopOrderDetailService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopOrderDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDERDETAIL)
    @PostMapping("/getAllShopOrderDetailPaging")
    public ResponseEntity<?> getAllShopOrderDetailPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = shopOrderDetailService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ShopOrderDetailDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopOrderDetailPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDERDETAIL)
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopOrderDetailById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopOrderDetailDto output = shopOrderDetailService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getShopOrderDetailById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDERDETAIL_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createShopOrderDetail(@RequestBody ShopOrderDetailInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopOrderDetailDto shopOrderDetail = shopOrderDetailService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, shopOrderDetail.getId());
        } catch (Exception e) {
            log.error("Error createShopOrderDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDERDETAIL_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editShopOrderDetail(@RequestBody ShopOrderDetailInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            shopOrderDetailService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editShopOrderDetail {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDERDETAIL_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopOrderDetailById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            shopOrderDetailService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteShopOrderDetailById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
