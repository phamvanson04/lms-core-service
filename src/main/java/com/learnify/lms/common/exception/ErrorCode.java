package com.learnify.lms.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  // 4XX - CLIENT ERROR CODES
  // 400 - Bad Request (1000-1099)
  INVALID_INPUT(1000, "INVALID_INPUT", "Invalid input data", HttpStatus.BAD_REQUEST),
  VALIDATION_ERROR(1001, "VALIDATION_ERROR", "Validation failed", HttpStatus.BAD_REQUEST),
  INVALID_EMAIL(
      1002,
      "INVALID_EMAIL",
      "Email must be in a valid format (e.g., user@example.com)",
      HttpStatus.BAD_REQUEST),
  INVALID_PASSWORD(
      1003,
      "INVALID_PASSWORD",
      "Password must be 8–20 chars, contain uppercase, lowercase, digit, and special character",
      HttpStatus.BAD_REQUEST),
  PASSWORD_UNMATCH(1004, "PASSWORD_UNMATCH", "Password does not match", HttpStatus.BAD_REQUEST),
  PASSWORD_NULL(1005, "PASSWORD_NULL", "Password cannot be null or empty", HttpStatus.BAD_REQUEST),
  INVALID_DATE_RANGE(
      1007, "INVALID_DATE_RANGE", "Start date must be before end date", HttpStatus.BAD_REQUEST),
  INVALID_FILE_FORMAT(
      1008,
      "INVALID_FILE_FORMAT",
      "Invalid file format. Allowed formats: PDF, DOCX, PPTX, MP4",
      HttpStatus.BAD_REQUEST),
  FILE_SIZE_EXCEEDED(
      1009, "FILE_SIZE_EXCEEDED", "File max size 5MB and with ", HttpStatus.BAD_REQUEST),
  INVALID_FILE_TYPE(
      1016,
      "INVALID_FILE_TYPE",
      "Invalid file type. Allowed types: jpeg,png,webp ",
      HttpStatus.BAD_REQUEST),
  INVALID_COURSE_DURATION(
      1010,
      "INVALID_COURSE_DURATION",
      "Course duration must be between 1 and 52 weeks",
      HttpStatus.BAD_REQUEST),
  INVALID_QUIZ_DURATION(
      1011,
      "INVALID_QUIZ_DURATION",
      "Quiz duration must be between 5 and 180 minutes",
      HttpStatus.BAD_REQUEST),
  INVALID_GRADE_VALUE(
      1012, "INVALID_GRADE_VALUE", "Grade must be between 0 and 100", HttpStatus.BAD_REQUEST),
  EMPTY_COURSE_CONTENT(
      1013, "EMPTY_COURSE_CONTENT", "Course must have at least one lesson", HttpStatus.BAD_REQUEST),
  INVALID_ENROLLMENT_CAPACITY(
      1014,
      "INVALID_ENROLLMENT_CAPACITY",
      "Enrollment capacity must be at least 1",
      HttpStatus.BAD_REQUEST),
  INVALID_PHONENUMBER(
      1015, "INVALID_PHONENUMBER", "Phone number must be a valid format", HttpStatus.BAD_REQUEST),
  PASSWORD_MISSING_SPECIAL_CHAR(
      1325,
      "PASSWORD_MISSING_SPECIAL_CHAR",
      "Password must contain at least one special character (@$!%*?&)",
      HttpStatus.BAD_REQUEST),
  FULLNAME_NOT_EMPTY(
      1326, "FULLNAME_NOT_EMPTY", "Full name cannot be empty", HttpStatus.BAD_REQUEST),
  INVALID_PHONE_NUMBER_VN(
      1327,
      "INVALID_PHONE_NUMBER_VN",
      "Phone number must be in a valid format for Vietnam +84",
      HttpStatus.BAD_REQUEST),

  // 401 - Unauthorized (1100-1199)
  UNAUTHORIZED(
      1100, "UNAUTHORIZED", "Authentication required. Please login", HttpStatus.UNAUTHORIZED),
  INVALID_CREDENTIALS(
      1101, "INVALID_CREDENTIALS", "Invalid email or password", HttpStatus.UNAUTHORIZED),
  TOKEN_EXPIRED(
      1102, "TOKEN_EXPIRED", "Token expired. Please login again", HttpStatus.UNAUTHORIZED),
  TOKEN_INVALID(1103, "TOKEN_INVALID", "Invalid token", HttpStatus.UNAUTHORIZED),
  SESSION_TERMINATED(
      1104, "SESSION_TERMINATED", "Session already terminated", HttpStatus.UNAUTHORIZED),
  NO_ACTIVE_SESSION(1105, "NO_ACTIVE_SESSION", "No active session found", HttpStatus.UNAUTHORIZED),
  ACCOUNT_LOCKED(
      1106, "ACCOUNT_LOCKED", "Account is locked. Please contact support", HttpStatus.UNAUTHORIZED),
  ACCOUNT_DISABLED(
      1107,
      "ACCOUNT_DISABLED",
      "Account is disabled. Please verify your email",
      HttpStatus.UNAUTHORIZED),
  EMAIL_NOT_VERIFIED(
      1108,
      "EMAIL_NOT_VERIFIED",
      "Email not verified. Please check your inbox",
      HttpStatus.UNAUTHORIZED),
  OTP_INVALID(1109, "OTP_INVALID", "Invalid OTP code", HttpStatus.UNAUTHORIZED),
  OTP_EXPIRED(1110, "OTP_EXPIRED", "OTP code has expired", HttpStatus.UNAUTHORIZED),
  INVALID_2FA_CODE(
      1111, "INVALID_2FA_CODE", "Invalid two-factor authentication code", HttpStatus.UNAUTHORIZED),
  INVALID_TOKEN(
      1112, "INVALID_TOKEN", "Invalid or missing authentication token", HttpStatus.UNAUTHORIZED),
  PHONE_EXISTS(1113, "PHONE_EXISTS", "Phone number already exists", HttpStatus.UNAUTHORIZED),
  USERNAME_CHANGE_TOO_SOON(
      1114,
      "USERNAME_CHANGE_TOO_SOON",
      "Username can only be changed once every 30 days",
      HttpStatus.UNAUTHORIZED),
  FILE_UPLOAD_FAILED(
      1115,
      "ERROR_FILE_UPLOAD_FAILED",
      "File upload failed. Please try again.",
      HttpStatus.UNAUTHORIZED),
  OTP_REQUEST_LIMIT_EXCEEDED(
      1116,
      "OTP_REQUEST_LIMIT_EXCEEDED",
      "Too many OTP requests. Please try again later",
      HttpStatus.UNAUTHORIZED),
  UNAUTHENTICATED(
      1117, "UNAUTHENTICATED", "You are not authenticated. Please login", HttpStatus.UNAUTHORIZED),
  SAME_PASSWORD(
      1118,
      "SAME_PASSWORD",
      "New password must be different from current password",
      HttpStatus.BAD_REQUEST),
  PHONE_EXISTED(1119, "PHONE_EXISTED", "Phone number is already in use", HttpStatus.CONFLICT),

  // 403 - Forbidden (1200-1299)
  FORBIDDEN(1200, "FORBIDDEN", "Access denied. You don't have permission", HttpStatus.FORBIDDEN),
  INSUFFICIENT_PERMISSIONS(
      1201,
      "INSUFFICIENT_PERMISSIONS",
      "Insufficient permissions for this action",
      HttpStatus.FORBIDDEN),
  NOT_COURSE_INSTRUCTOR(
      1202,
      "NOT_COURSE_INSTRUCTOR",
      "Only course instructor can perform this action",
      HttpStatus.FORBIDDEN),
  NOT_ENROLLED(1203, "NOT_ENROLLED", "You must be enrolled in this course", HttpStatus.FORBIDDEN),
  COURSE_NOT_PUBLISHED(
      1204, "COURSE_NOT_PUBLISHED", "Course is not published yet", HttpStatus.FORBIDDEN),
  ENROLLMENT_CLOSED(1205, "ENROLLMENT_CLOSED", "Enrollment period has ended", HttpStatus.FORBIDDEN),
  QUIZ_NOT_AVAILABLE(
      1206, "QUIZ_NOT_AVAILABLE", "Quiz is not available at this time", HttpStatus.FORBIDDEN),
  ASSIGNMENT_DEADLINE_PASSED(
      1207, "ASSIGNMENT_DEADLINE_PASSED", "Assignment deadline has passed", HttpStatus.FORBIDDEN),
  ALREADY_SUBMITTED(
      1208,
      "ALREADY_SUBMITTED",
      "You have already submitted this assignment",
      HttpStatus.FORBIDDEN),
  QUIZ_ALREADY_COMPLETED(
      1209, "QUIZ_ALREADY_COMPLETED", "You have already completed this quiz", HttpStatus.FORBIDDEN),
  ROLE_NOT_FOUND(1210, "ROLE_NOT_FOUND", "Role not found in the system", HttpStatus.FORBIDDEN),
  MAX_ATTEMPTS_REACHED(
      1210, "MAX_ATTEMPTS_REACHED", "Maximum quiz attempts reached", HttpStatus.FORBIDDEN),

  // 404 - Not Found (1300-1399)
  NOT_FOUND(1300, "NOT_FOUND", "Resource not found", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND(1301, "USER_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND),
  COURSE_NOT_FOUND(1302, "COURSE_NOT_FOUND", "Course not found", HttpStatus.NOT_FOUND),
  LESSON_NOT_FOUND(1303, "LESSON_NOT_FOUND", "Lesson not found", HttpStatus.NOT_FOUND),
  ASSIGNMENT_NOT_FOUND(1304, "ASSIGNMENT_NOT_FOUND", "Assignment not found", HttpStatus.NOT_FOUND),
  QUIZ_NOT_FOUND(1305, "QUIZ_NOT_FOUND", "Quiz not found", HttpStatus.NOT_FOUND),
  ENROLLMENT_NOT_FOUND(1306, "ENROLLMENT_NOT_FOUND", "Enrollment not found", HttpStatus.NOT_FOUND),
  SUBMISSION_NOT_FOUND(1307, "SUBMISSION_NOT_FOUND", "Submission not found", HttpStatus.NOT_FOUND),
  CATEGORY_NOT_FOUND(1308, "CATEGORY_NOT_FOUND", "Category not found", HttpStatus.NOT_FOUND),
  GRADE_NOT_FOUND(1309, "GRADE_NOT_FOUND", "Grade not found", HttpStatus.NOT_FOUND),
  QUESTION_NOT_FOUND(1310, "QUESTION_NOT_FOUND", "Question not found", HttpStatus.NOT_FOUND),
  DISCUSSION_NOT_FOUND(1311, "DISCUSSION_NOT_FOUND", "Discussion not found", HttpStatus.NOT_FOUND),
  COMMENT_NOT_FOUND(1312, "COMMENT_NOT_FOUND", "Comment not found", HttpStatus.NOT_FOUND),
  NOTIFICATION_NOT_FOUND(
      1313, "NOTIFICATION_NOT_FOUND", "Notification not found", HttpStatus.NOT_FOUND),
  INVALID_EMAIL_FORMAT(
      1314,
      "INVALID_EMAIL_FORMAT",
      "Please provide a valid Gmail address (example@gmail.com)",
      HttpStatus.BAD_REQUEST),

  // 409 - Conflict (1400-1499)
  CONFLICT(1400, "CONFLICT", "Resource conflict", HttpStatus.CONFLICT),
  USERNAME_EXISTS(1401, "USERNAME_EXISTS", "Username already exists", HttpStatus.CONFLICT),
  EMAIL_EXISTS(1402, "EMAIL_EXISTS", "Email already exists", HttpStatus.CONFLICT),
  COURSE_CODE_EXISTS(1403, "COURSE_CODE_EXISTS", "Course code already exists", HttpStatus.CONFLICT),
  ALREADY_ENROLLED(
      1404, "ALREADY_ENROLLED", "Already enrolled in this course", HttpStatus.CONFLICT),
  ENROLLMENT_FULL(1405, "ENROLLMENT_FULL", "Course enrollment is full", HttpStatus.CONFLICT),
  COURSE_ALREADY_PUBLISHED(
      1406, "COURSE_ALREADY_PUBLISHED", "Course is already published", HttpStatus.CONFLICT),
  DUPLICATE_LESSON_ORDER(
      1407,
      "DUPLICATE_LESSON_ORDER",
      "Lesson order already exists in this course",
      HttpStatus.CONFLICT),
  CATEGORY_NAME_EXISTS(
      1408, "CATEGORY_NAME_EXISTS", "Category name already exists", HttpStatus.CONFLICT),

  // 410 - Gone (1500-1509)
  RESOURCE_DELETED(1500, "RESOURCE_DELETED", "Resource has been deleted", HttpStatus.GONE),
  COURSE_ARCHIVED(1501, "COURSE_ARCHIVED", "Course has been archived", HttpStatus.GONE),
  ENROLLMENT_CANCELLED(
      1502, "ENROLLMENT_CANCELLED", "Enrollment has been cancelled", HttpStatus.GONE),

  // 422 - Unprocessable Entity (1510-1529)
  UNPROCESSABLE_ENTITY(
      1510, "UNPROCESSABLE_ENTITY", "Unable to process request", HttpStatus.UNPROCESSABLE_ENTITY),
  PREREQUISITE_NOT_MET(
      1511,
      "PREREQUISITE_NOT_MET",
      "Course prerequisites not met",
      HttpStatus.UNPROCESSABLE_ENTITY),
  COURSE_NOT_COMPLETED(
      1512,
      "COURSE_NOT_COMPLETED",
      "Previous course must be completed first",
      HttpStatus.UNPROCESSABLE_ENTITY),
  INVALID_COURSE_STATUS(
      1513,
      "INVALID_COURSE_STATUS",
      "Invalid course status transition",
      HttpStatus.UNPROCESSABLE_ENTITY),
  CANNOT_DELETE_ACTIVE_COURSE(
      1514,
      "CANNOT_DELETE_ACTIVE_COURSE",
      "Cannot delete course with active enrollments",
      HttpStatus.UNPROCESSABLE_ENTITY),
  CANNOT_EDIT_PUBLISHED_QUIZ(
      1515,
      "CANNOT_EDIT_PUBLISHED_QUIZ",
      "Cannot edit quiz after publication",
      HttpStatus.UNPROCESSABLE_ENTITY),
  INCOMPLETE_COURSE_STRUCTURE(
      1516,
      "INCOMPLETE_COURSE_STRUCTURE",
      "Course must have valid structure before publishing",
      HttpStatus.UNPROCESSABLE_ENTITY),

  // 429 - Too Many Requests (1530-1539)
  TOO_MANY_REQUESTS(
      1530,
      "TOO_MANY_REQUESTS",
      "Too many requests. Please try again later",
      HttpStatus.TOO_MANY_REQUESTS),
  LOGIN_ATTEMPTS_EXCEEDED(
      1531,
      "LOGIN_ATTEMPTS_EXCEEDED",
      "Too many login attempts. Please try again in 15 minutes",
      HttpStatus.TOO_MANY_REQUESTS),
  SUBMISSION_RATE_LIMIT(
      1532,
      "SUBMISSION_RATE_LIMIT",
      "Too many submissions. Please wait before submitting again",
      HttpStatus.TOO_MANY_REQUESTS),

  // ============================================================================
  // 5XX - SERVER ERROR CODES
  // ============================================================================

  // 500 - Internal Server Error (2000-2099)
  INTERNAL_ERROR(
      2000,
      "INTERNAL_ERROR",
      "Internal server error. Please try again later",
      HttpStatus.INTERNAL_SERVER_ERROR),
  UNCATEGORIZED_EXCEPTION(
      2001,
      "UNCATEGORIZED_EXCEPTION",
      "An unexpected error occurred",
      HttpStatus.INTERNAL_SERVER_ERROR),
  DATABASE_ERROR(
      2002, "DATABASE_ERROR", "Database operation failed", HttpStatus.INTERNAL_SERVER_ERROR),
  FILE_UPLOAD_ERROR(
      2003, "FILE_UPLOAD_ERROR", "Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR),
  FILE_DELETE_ERROR(
      2004, "FILE_DELETE_ERROR", "Failed to delete file", HttpStatus.INTERNAL_SERVER_ERROR),
  EMAIL_SEND_ERROR(
      2005, "EMAIL_SEND_ERROR", "Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR),
  NOTIFICATION_ERROR(
      2006, "NOTIFICATION_ERROR", "Failed to send notification", HttpStatus.INTERNAL_SERVER_ERROR),
  GRADE_CALCULATION_ERROR(
      2007,
      "GRADE_CALCULATION_ERROR",
      "Failed to calculate grade",
      HttpStatus.INTERNAL_SERVER_ERROR),
  REPORT_GENERATION_ERROR(
      2008,
      "REPORT_GENERATION_ERROR",
      "Failed to generate report",
      HttpStatus.INTERNAL_SERVER_ERROR),

  // 502 - Bad Gateway (2100-2109)
  BAD_GATEWAY(2100, "BAD_GATEWAY", "Bad gateway error", HttpStatus.BAD_GATEWAY),
  EXTERNAL_SERVICE_ERROR(
      2101, "EXTERNAL_SERVICE_ERROR", "External service unavailable", HttpStatus.BAD_GATEWAY),
  PAYMENT_SERVICE_ERROR(
      2102, "PAYMENT_SERVICE_ERROR", "Payment service unavailable", HttpStatus.BAD_GATEWAY),

  // 503 - Service Unavailable (2110-2119)
  SERVICE_UNAVAILABLE(
      2110,
      "SERVICE_UNAVAILABLE",
      "Service temporarily unavailable",
      HttpStatus.SERVICE_UNAVAILABLE),
  MAINTENANCE_MODE(
      2111, "MAINTENANCE_MODE", "System is under maintenance", HttpStatus.SERVICE_UNAVAILABLE),

  // 504 - Gateway Timeout (2120-2129)
  GATEWAY_TIMEOUT(2120, "GATEWAY_TIMEOUT", "Request timeout", HttpStatus.GATEWAY_TIMEOUT),
  OPERATION_TIMEOUT(
      2121, "OPERATION_TIMEOUT", "Operation took too long to complete", HttpStatus.GATEWAY_TIMEOUT);

  private final int code;
  private final String errorCode;
  private final String message;
  private final HttpStatusCode statusCode;

  ErrorCode(int code, String errorCode, String message, HttpStatus statusCode) {
    this.code = code;
    this.errorCode = errorCode;
    this.message = message;
    this.statusCode = statusCode;
  }

  public int getCode() {
    return code;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatusCode getStatusCode() {
    return statusCode;
  }

  public static ErrorCode fromCode(int code) {
    for (ErrorCode ec : values()) {
      if (ec.code == code) {
        return ec;
      }
    }
    return UNCATEGORIZED_EXCEPTION;
  }

  public static ErrorCode fromErrorCode(String errorCode) {
    for (ErrorCode ec : values()) {
      if (ec.errorCode.equals(errorCode)) {
        return ec;
      }
    }
    return UNCATEGORIZED_EXCEPTION;
  }
}
