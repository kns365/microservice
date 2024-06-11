package vn.com.kns.portalapi.web.controller.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.category.groupItem.GroupItemService;
import vn.com.kns.portalapi.application.service.category.groupItem.dto.GroupItemDto;
import vn.com.kns.portalapi.application.service.category.groupItem.dto.GroupItemInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/groupItems")
public class GroupItemController {

    @Autowired
    private GroupItemService groupItemService;

    @PreAuthorize(HasPrivilegeConst.GROUPITEM)
    @GetMapping("/")
    public ResponseEntity<?> getAllGroupItem() {
        ResponseDto response = new ResponseDto();
        try {
            List<GroupItemDto> output  = groupItemService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllGroupItem {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.GROUPITEM)
    @PostMapping("/getAllGroupItemPaging")
    public ResponseEntity<?> getAllGroupItemPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = groupItemService.getAllPaging(new PagingInput(input));
            DataTablesOutput<GroupItemDto> output  = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllGroupItemPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.GROUPITEM)
    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupItemById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            GroupItemDto output  = groupItemService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getGroupItemById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.GROUPITEM_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createGroupItem(@RequestBody GroupItemInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            groupItemService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error createGroupItem {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.GROUPITEM_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editGroupItem(@RequestBody GroupItemInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            groupItemService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editGroupItem {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.GROUPITEM_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteGroupItemById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            groupItemService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteGroupItemById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
