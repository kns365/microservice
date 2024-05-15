package com.kns.apps.microservice.authservice.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request, HttpServletResponse response) {
        Date execDurStart = new Date();
        Exception exception = ex;
        try {
            //AuditLogHelper.create(ServiceNameConst.Exception_AccessDenied, null, null, null, request, response, execDurStart, exception);
//            AuditLogHelper.create(request, response, execDurStart, exception);
        } catch (Exception e) {
            log.error("Error handleAccessDeniedException {}", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<?> handleException(Exception ex) {
//        log.error("handleException");
//        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
