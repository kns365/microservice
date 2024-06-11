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
import vn.com.kns.portalapi.core.model.ResponseDto;
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
        ResponseDto response = new ResponseDto();
        try {
            List<ItemDto> output = itemService.getAll(null);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllItem {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM)
    @GetMapping("/getAllItemByGroupItemId/{id}")
    public ResponseEntity<?> getAllItemByGroupItemId(@PathVariable("id") Long groupItemId) {
        ResponseDto response = new ResponseDto();
        try {
            List<ItemDto> output = itemService.getAll(groupItemId);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllItem {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM)
    @PostMapping("/getAllItemPaging")
    public ResponseEntity<?> getAllItemPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = itemService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ItemDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllItemPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM)
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ItemDto output = itemService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getItemById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createItem(@RequestBody ItemInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            ItemDto item = itemService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, item.getId());
        } catch (Exception e) {
            log.error("Error createItem {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editItem(@RequestBody ItemInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            itemService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editItem {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.ITEM_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            itemService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteItemById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
