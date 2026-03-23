package com.learnify.lms.common.exception;

import com.learnify.lms.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(AppException.class)
  public ResponseEntity<BaseResponse<?>> handleAppException(AppException ex) {
    ErrorCode ec = ex.getErrorCode();
    return ResponseEntity.status(ec.getStatusCode()).body(BaseResponse.error(ec, ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse<?>> handleValidation(MethodArgumentNotValidException ex) {
    String message =
        ex.getBindingResult().getFieldError() != null
            ? ex.getBindingResult().getFieldError().getDefaultMessage()
            : ErrorCode.VALIDATION_ERROR.getMessage();
    return ResponseEntity.badRequest()
        .body(BaseResponse.error(ErrorCode.VALIDATION_ERROR, message));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<?>> handleUnknown(Exception ex) {
    log.error("Unhandled exception", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error(ErrorCode.INTERNAL_ERROR, null));
  }

  @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
  public ResponseEntity<BaseResponse<?>> handleAccessDenied(AccessDeniedException ex) {

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(BaseResponse.error(ErrorCode.FORBIDDEN, ErrorCode.FORBIDDEN.getMessage()));
  }
}

