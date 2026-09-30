package com.homework.jbiocpthb260515_phamgiatrung.exception;

import com.homework.jbiocpthb260515_phamgiatrung.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApi(ApiException ex) {
        return ApiResponse.error(ex.getStatus(), ex.getMessage(), ex.getErrors());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(BindException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                e -> errors.putIfAbsent(e.getField(), e.getDefaultMessage())
        );
        return ApiResponse.error(HttpStatus.BAD_REQUEST, "Dữ liệu không hợp lệ", errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleTypeMisMatch(MethodArgumentTypeMismatchException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, "Tham số không hợp lệ",
                Map.of(ex.getName(), "Giá trị không đúng định dạng"));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxSize(MaxUploadSizeExceededException ex) {
        return ApiResponse.error(HttpStatus.PAYLOAD_TOO_LARGE, "File vượt quá dung lượng cho phép (5MB)", null);
    }
}
