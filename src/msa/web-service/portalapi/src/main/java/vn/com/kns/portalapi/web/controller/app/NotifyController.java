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
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.util.Date;
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
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<NotifyDto> output = notifyService.getAll(null, null);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllNotify {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    //@PreAuthorize(HasPrivilegeConst.NOTIFY)
    @GetMapping("")
    public ResponseEntity<?> getNotification(@RequestParam(value = "username", required = true) String username,
                                             @RequestParam(value = "max", required = true) Integer max) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<NotifyDto> data = notifyService.getAll(username, max);
            NotificationDto output = new NotificationDto(data);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllNotify {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    //@PreAuthorize(HasPrivilegeConst.NOTIFY)
    @PostMapping("/getAllNotifyPaging")
    public ResponseEntity<ResponseDto> getAllNotifyPaging(@RequestBody DataTablesInput input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            PagingOutput paging = notifyService.getAllPaging(new PagingInput(input));
            DataTablesOutput<NotifyDto> output = new DataTablesOutput(input, paging);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllNotifyPaging {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    //@PreAuthorize(HasPrivilegeConst.NOTIFY)
    @GetMapping("/{id}")
    public ResponseEntity<?> getNotifyById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            NotifyDto output = notifyService.getById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getNotifyById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping("/readNotifyById/{id}")
    public ResponseEntity<?> readNotifyById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            NotifyDto output = notifyService.readNotifyById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getNotifyById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.NOTIFY_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createNotify(@RequestBody NotifyInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            notifyService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error createNotify {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.NOTIFY_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editNotify(@RequestBody NotifyInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            notifyService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editNotify {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.NOTIFY_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteNotifyById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            notifyService.deleteById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deleteNotifyById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

}
