package com.kns.apps.microservice.configserver.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kns.apps.microservice.configserver.core.constant.ResponseStatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {
    private Integer errorCode;
    private String errorMessage;
    private String status = ResponseStatusCode.FAILURE.name(); //SUCCESS - FAILURE
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResponseDto(Integer errorCode, String errorMessage, Object data) {
        if (errorCode == ResponseStatusCode.SUCCESS.getVal() || errorCode == ResponseStatusCode.SUCCESS_200.getVal()) {
            this.status = ResponseStatusCode.SUCCESS.name();
        }
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

//     "timestamp": "2024-05-13T08:19:13.457+0000",
//     "status": 500,
//     "error": "Internal Server Error",
//     "message": "GENERAL"
}
