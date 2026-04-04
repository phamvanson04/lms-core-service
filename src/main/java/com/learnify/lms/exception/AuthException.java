package com.learnify.lms.exception;

public class AuthException extends AppException {

  public AuthException(ErrorCode errorCode) {
    super(errorCode);
  }

  public AuthException(ErrorCode errorCode, String customMessage) {
    super(errorCode, customMessage);
  }

  public AuthException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
