package vn.com.kns.portalapi.web.controller.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.notify.NotifyService;
import vn.com.kns.portalapi.application.service.notify.dto.NotificationDto;
import vn.com.kns.portalapi.application.service.notify.dto.NotifyDto;
import vn.com.kns.portalapi.application.service.notify.dto.NotifyInputDto;
import vn.com.kns.portalapi.application.service.shop.shop.dto.ShopDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/notifies")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    //@PreAuthorize(HasPrivilegeConst.NOTIFY)
    @GetMapping("/")
    public ResponseEntity<?> getAllNotify() {
        ResponseDto response = new ResponseDto();
        try {
            List<NotifyDto> output = notifyService.getAll(null, null);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllNotify {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //@PreAuthorize(HasPrivilegeConst.NOTIFY)
    @GetMapping("")
    public ResponseEntity<?> getNotification(@RequestParam(value = "username", required = true) String username,
                                             @RequestParam(value = "max", required = true) Integer max) {
        ResponseDto response = new ResponseDto();
        try {
            List<NotifyDto> data = notifyService.getAll(username, max);
            NotificationDto output = new NotificationDto(data);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllNotify {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //@PreAuthorize(HasPrivilegeConst.NOTIFY)
    @PostMapping("/getAllNotifyPaging")
    public ResponseEntity<ResponseDto> getAllNotifyPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = notifyService.getAllPaging(new PagingInput(input));
            DataTablesOutput<NotifyDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllNotifyPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //@PreAuthorize(HasPrivilegeConst.NOTIFY)
    @GetMapping("/{id}")
    public ResponseEntity<?> getNotifyById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            NotifyDto output = notifyService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getNotifyById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/readNotifyById/{id}")
    public ResponseEntity<?> readNotifyById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            NotifyDto output = notifyService.readNotifyById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getNotifyById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.NOTIFY_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createNotify(@RequestBody NotifyInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            notifyService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error createNotify {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.NOTIFY_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editNotify(@RequestBody NotifyInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            notifyService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editNotify {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.NOTIFY_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteNotifyById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            notifyService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteNotifyById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
