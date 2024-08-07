package vn.com.kns.portalapi.web.controller.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.kns.portalapi.application.helper.AuditLogHelper;
import vn.com.kns.portalapi.application.service.category.district.DistrictService;
import vn.com.kns.portalapi.application.service.category.district.dto.DistrictDto;
import vn.com.kns.portalapi.application.service.category.district.dto.DistrictInputDto;
import vn.com.kns.portalapi.core.constant.HasPrivilegeConst;
import vn.com.kns.portalapi.core.constant.MethodNameConst;
import vn.com.kns.portalapi.core.constant.ServiceNameConst;
import vn.com.kns.portalapi.core.model.PagingInput;
import vn.com.kns.portalapi.core.model.PagingOutput;
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kns.apps.msa.commonpack.application.helper.LogHelper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.util.Date;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesOutput;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/districts")
public class DistrictController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private DistrictService districtService;

    @PreAuthorize(HasPrivilegeConst.DISTRICT)
    @GetMapping("/")
    public ResponseEntity<?> getAllDistrict() {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<DistrictDto> output = districtService.getAll(null);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllDistrict {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, null, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.DISTRICT)
    @GetMapping("/getAllDistrictByProvinceId/{provinceId}")
    public ResponseEntity<?> getAllDistrictByProvinceId(@PathVariable("provinceId") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            List<DistrictDto> output = districtService.getAll(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllDistrict {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.DISTRICT)
    @PostMapping("/getAllDistrictPaging")
    public ResponseEntity<?> getAllDistrictPaging(@RequestBody DataTablesInput input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            PagingOutput paging = districtService.getAllPaging(new PagingInput(input));
            DataTablesOutput<DistrictDto> output = new DataTablesOutput(input, paging);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getAllDistrictPaging {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.DISTRICT)
    @GetMapping("/{id}")
    public ResponseEntity<?> getDistrictById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            DistrictDto output = districtService.getById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, output);
        } catch (Exception e) {
            log.error("Error getDistrictById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.DISTRICT_CREATE)
    @PostMapping("/")
    public ResponseEntity<?> createDistrict(@RequestBody DistrictInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            districtService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error createDistrict {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.DISTRICT_EDIT)
    @PutMapping("/")
    public ResponseEntity<?> editDistrict(@RequestBody DistrictInputDto input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            districtService.createOrEdit(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error editDistrict {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PreAuthorize(HasPrivilegeConst.DISTRICT_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteDistrictById(@PathVariable("id") Long input) {
        Date execDurStart = new Date();
        ResponseDto res = new ResponseDto();
        try {
            districtService.deleteById(input);
            res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        } catch (Exception e) {
            log.error("Error deleteDistrictById {}", e.getMessage());
            res = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionUtils.getStackTrace(e), null, null);
        } finally {
            LogHelper.push(request, response, input, res.getData(), execDurStart, res.getErrorMessage());
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

}
