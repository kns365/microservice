package com.kns.apps.msa.commonpack.controller;

import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class BaseController {
    public ResponseEntity<ResponseDto> defaultFallback() {
        log.info("defaultFallback ... ");
        ResponseDto res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, null);
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
