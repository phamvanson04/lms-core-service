package com.learnify.lms.domain.exception;

public class FileUploadException extends AppException {

  public FileUploadException(ErrorCode errorCode) {
    super(errorCode);
  }

  public FileUploadException(ErrorCode errorCode, String customMessage) {
    super(errorCode, customMessage);
  }

  public FileUploadException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
