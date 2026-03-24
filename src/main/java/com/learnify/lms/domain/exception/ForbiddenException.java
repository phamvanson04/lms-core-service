package com.learnify.lms.domain.exception;

public class ForbiddenException extends RuntimeException {
  private final ErrorCode errorCode;

  public ForbiddenException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ForbiddenException(ErrorCode errorCode, String customMessage) {
    super(customMessage);
    this.errorCode = errorCode;
  }

  public ForbiddenException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }
}
