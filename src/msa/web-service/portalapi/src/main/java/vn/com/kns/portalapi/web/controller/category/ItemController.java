package vn.com.kns.portalapi.web.controller.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.category.item.ItemService;
import vn.com.kns.portalapi.application.service.category.item.dto.ItemDto;
import vn.com.kns.portalapi.application.service.category.item.dto.ItemInputDto;
import vn.com.kns.portalapi.application.service.category.province.dto.ProvinceDto;
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
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PreAuthorize(HasPrivilegeConst.ITEM)
    @GetMapping("/")
    public ResponseEntity<?> getAllItem() {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<ItemDto> output = itemService.getAll(null);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllItem {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM)
    @GetMapping("/getAllItemByGroupItemId/{id}")
    public ResponseEntity<?> getAllItemByGroupItemId(@PathVariable("id") Long groupItemId) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<ItemDto> output = itemService.getAll(groupItemId);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllItem {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM)
    @PostMapping("/getAllItemPaging")
    public ResponseEntity<?> getAllItemPaging(@RequestBody DataTablesInput input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            PagingOutput paging = itemService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ItemDto> output = new DataTablesOutput(input, paging);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllItemPaging {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM)
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            ItemDto output = itemService.getById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getItemById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createItem(@RequestBody ItemInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            ItemDto item = itemService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, item.getId());
        } catch (Exception e) {
            log.error("Error createItem {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editItem(@RequestBody ItemInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            itemService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editItem {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            itemService.deleteById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deleteItemById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

}
