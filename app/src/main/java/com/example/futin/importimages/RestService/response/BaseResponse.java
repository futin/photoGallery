package com.example.futin.importimages.RestService.response;

import org.springframework.http.HttpStatus;

/**
 * Created by Futin on 12/22/2015.
 */
public class BaseResponse {
    HttpStatus status;
    String statusName;
    ;

    public BaseResponse(HttpStatus status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public BaseResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }
}
