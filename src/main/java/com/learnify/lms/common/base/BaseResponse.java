package com.learnify.lms.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learnify.lms.common.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
  private boolean success;
  private int status;
  private String message;
  private T result;
  private LocalDateTime timestamp;
  private Error error;

  @Data
  @Builder
  public static class Error {
    private String errorCode;
    private String message;
  }

  public static <T> BaseResponse<T> success(T result, String message) {

    return BaseResponse.<T>builder()
        .success(true)
        .status(HttpStatus.OK.value())
        .message(message)
        .result(result)
        .timestamp(LocalDateTime.now())
        .build();
  }

  public static <T> BaseResponse<T> create(T data, String message) {

    return BaseResponse.<T>builder()
        .success(true)
        .status(HttpStatus.CREATED.value())
        .message(message)
        .result(data)
        .timestamp(LocalDateTime.now())
        .build();
  }

  public static BaseResponse<?> error(ErrorCode errorCode, String customMessage) {
    String message = customMessage != null ? customMessage : errorCode.getMessage();

    return BaseResponse.builder()
        .success(false)
        .status(errorCode.getStatusCode().value())
        .error(Error.builder().errorCode(errorCode.name()).message(message).build())
        .timestamp(LocalDateTime.now())
        .build();
  }
}

