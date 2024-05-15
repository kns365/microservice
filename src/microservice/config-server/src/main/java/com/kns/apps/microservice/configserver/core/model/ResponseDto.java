package com.kns.apps.microservice.configserver.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kns.apps.microservice.configserver.application.helper.DateHelper;
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
    private String timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String transactionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResponseDto(Integer errorCode, String errorMessage, String transactionId, Object data) {
        if (errorCode == ResponseStatusCode.SUCCESS.getVal() || errorCode == ResponseStatusCode.SUCCESS_200.getVal()) {
            this.status = ResponseStatusCode.SUCCESS.name();
        }
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = DateHelper.getDateStr("yyyy-MM-dd HH:mm:ss", null);
        this.transactionId = transactionId;
        this.data = data;
    }

//     "timestamp": "2024-05-13T08:19:13.457+0000",
//     "status": 500,
//     "error": "Internal Server Error",
//     "message": "GENERAL"

//    {
//        "errorCode": 200,
//            "errorMessage": "OK",
//            "status": "SUCCESS",
//            "timestamp": "2024-05-14 15:48:23",
//            "data": {
//                "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIkNSRUFURSIsIkVESVQiLCJSRU1PVkUiLCJST0xFX0FETUlOIiwiVklFVyJdLCJpYXQiOjE3MTU2NzY1MDIsImV4cCI6MTcxNTc2MjkwMn0.7nkVlZQaSJC1ZskpYblvh_HV3cK0bIyFEFGQaZMnM7XX60H24BQG1oesCAy5ejRy2llVCwaVwTfkw11DdZTl0w",
//                "tokenType": "Bearer ",
//                "expiresIn": "1715762902898",
//                "refreshToken": ""
//        }
//    }
}
