package vn.com.kns.portalapi.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.service.category.contact.ContactService;
import vn.com.kns.portalapi.application.service.category.contact.dto.ContactDto;
import vn.com.kns.portalapi.application.service.category.contact.dto.ContactInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PreAuthorize(HasPrivilegeConst.COUNTRY)
    @GetMapping("/")
    public ResponseEntity<?> getAllContact() {
        ResponseDto response = new ResponseDto();
        try {
            List<ContactDto> output = contactService.getAll();
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllContact {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.COUNTRY)
    @PostMapping("/getAllContactPaging")
    public ResponseEntity<?> getAllContactPaging(@RequestBody DataTablesInput input) {
        ResponseDto response = new ResponseDto();
        try {
            PagingOutput paging = contactService.getAllPaging(new PagingInput(input));
            DataTablesOutput<ContactDto> output = new DataTablesOutput(input, paging);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getAllContactPaging {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.COUNTRY)
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            ContactDto output = contactService.getById(input);
            response = new ResponseDto(HttpStatus.OK, output);
        } catch (Exception e) {
            log.error("Error getContactById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.COUNTRY_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createContact(@RequestBody ContactInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            contactService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error createContact {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.COUNTRY_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editContact(@RequestBody ContactInputDto input) {
        ResponseDto response = new ResponseDto();
        try {
            contactService.createOrEdit(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error editContact {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.COUNTRY_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteContactById(@PathVariable("id") Long input) {
        ResponseDto response = new ResponseDto();
        try {
            contactService.deleteById(input);
            response = new ResponseDto(HttpStatus.OK, null);
        } catch (Exception e) {
            log.error("Error deleteContactById {}", e.getMessage());
            response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
