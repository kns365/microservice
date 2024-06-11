package vn.com.kns.portalapi.web.controller.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.shop.shop.ShopService;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopDto;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PreAuthorize(HasPrivilegeConst.SHOP)
    @GetMapping("/")
    public ResponseEntity<?> getAllShop() {
        ResponseDto response = new ResponseDto();
        try {
            List<ShopDto> output = shopService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShop {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP)
    @PostMapping("/getAllShopPaging")
    public ResponseEntity<?> getAllShopPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = shopService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ShopDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllShopPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP)
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopDto output = shopService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getShopById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP)
    @GetMapping("")
    public ResponseEntity<?> getShopByCode(@RequestParam(value = "code", required = false) String code) {
        ResponseDto response = new ResponseDto();
        try {
            ShopDto output = shopService.getByCode(code);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getShopByCode {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createShop(@RequestBody ShopInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            ShopDto shop = shopService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, shop.getId());
        } catch (Exception e) {
            log.error("Error createShop {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editShop(@RequestBody ShopInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            shopService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editShop {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            shopService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteShopById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
