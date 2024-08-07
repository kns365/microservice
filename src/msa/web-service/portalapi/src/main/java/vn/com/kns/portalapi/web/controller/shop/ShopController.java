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
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.util.Date;
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
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<ShopDto> output = shopService.getAll();
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllShop {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP)
    @PostMapping("/getAllShopPaging")
    public ResponseEntity<?> getAllShopPaging(@RequestBody DataTablesInput input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            PagingOutput paging = shopService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ShopDto> output = new DataTablesOutput(input, paging);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllShopPaging {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP)
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            ShopDto output = shopService.getById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getShopById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP)
    @GetMapping("")
    public ResponseEntity<?> getShopByCode(@RequestParam(value = "code", required = false) String code) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            ShopDto output = shopService.getByCode(code);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getShopByCode {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createShop(@RequestBody ShopInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            ShopDto shop = shopService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, shop.getId());
        } catch (Exception e) {
            log.error("Error createShop {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editShop(@RequestBody ShopInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            shopService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editShop {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.SHOP_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            shopService.deleteById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deleteShopById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

}
