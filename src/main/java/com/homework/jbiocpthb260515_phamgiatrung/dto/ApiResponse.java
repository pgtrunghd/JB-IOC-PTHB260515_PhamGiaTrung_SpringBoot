package com.homework.jbiocpthb260515_phamgiatrung.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Object errors;
    private int httpStatus;

    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status)
                .body(new ApiResponse<>(true, message, data, null, status.value()));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, Object errors) {
        return ResponseEntity.status(status)
                .body(new ApiResponse<>(false, message, null, errors, status.value()));
    }
}
