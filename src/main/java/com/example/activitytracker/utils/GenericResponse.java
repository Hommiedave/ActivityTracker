package com.example.activitytracker.utils;
//import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
public class GenericResponse {

    private String message;
    private String status;

    @JsonIgnore
    private HttpStatus httpStatus;
    private Object data;

    public GenericResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public GenericResponse(String message, String status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public GenericResponse(String message, String status, HttpStatus httpStatus) {
        this.message = message;
        this.status = status;
        this.httpStatus = httpStatus;
    }

    public GenericResponse(String message, String status,Object data, HttpStatus httpStatus) {
        this.message = message;
        this.status = status;
        this.httpStatus = httpStatus;
        this.data = data;
    }
}
