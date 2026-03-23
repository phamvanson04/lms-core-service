package com.learnify.lms.common.exception;

public class CourseException extends AppException {

  public CourseException(ErrorCode errorCode) {
    super(errorCode);
  }

  public CourseException(ErrorCode errorCode, String customMessage) {
    super(errorCode, customMessage);
  }

  public CourseException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
