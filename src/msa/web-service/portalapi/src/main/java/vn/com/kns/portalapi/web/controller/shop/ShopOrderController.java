package vn.com.kns.portalapi.web.controller.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.shop.shopOrder.ShopOrderService;
import vn.com.kns.portalapi.application.service.shop.shopOrder.dto.ShopOrderDto;
import vn.com.kns.portalapi.application.service.shop.shopOrder.dto.ShopOrderInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shopOrders")
public class ShopOrderController {

    @Autowired
    private ShopOrderService shopOrderService;

    @PreAuthorize(HasPrivilegeConst.SHOPORDER)
    @GetMapping("/")
    public ResponseEntity<?> getAllShopOrder() {
        ResponseDto response = new ResponseDto();
        try {
            List<ShopOrderDto> output = shopOrderService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopOrder {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDER)
    @PostMapping("/getAllShopOrderPaging")
    public ResponseEntity<?> getAllShopOrderPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = shopOrderService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ShopOrderDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopOrderPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDER)
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopOrderById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopOrderDto output = shopOrderService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getShopOrderById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDER_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createShopOrder(@RequestBody ShopOrderInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopOrderDto shopOrder = shopOrderService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, shopOrder.getId());
        } catch (Exception e) {
            log.error("Error createShopOrder {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDER_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editShopOrder(@RequestBody ShopOrderInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            shopOrderService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editShopOrder {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOPORDER_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopOrderById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            shopOrderService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteShopOrderById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
