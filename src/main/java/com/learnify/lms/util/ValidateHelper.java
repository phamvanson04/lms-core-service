package com.learnify.lms.util;

import java.util.regex.Pattern;

public class ValidateHelper {
  private static final Pattern EMAIL_PATTERN =
      Pattern.compile(
          "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
  private static final Pattern PHONE_VN_PATTERN =
      Pattern.compile("^0(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$");
  private static final Pattern PHONE_INTERNATIONAL_PATTERN =
      Pattern.compile(
          "^\\+?[1-9]\\d{1,14}$" // E.164 format
          );

  private static final Pattern PASSWORD_STRONG_PATTERN =
      Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

  private static final Pattern COUPON_CODE_PATTERN = Pattern.compile("^[A-Z0-9-]{4,20}$");

  public static Boolean validateEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }
    email = email.trim();
    if (email.length() > 254) {
      return false;
    }
    return EMAIL_PATTERN.matcher(email).matches();
  }

  public static boolean isValidPhoneVN(String phone) {
    if (phone == null || phone.trim().isEmpty()) {
      return false;
    }
    phone = phone.replaceAll("[\\s\\-()]", "");

    return PHONE_VN_PATTERN.matcher(phone).matches();
  }

  public static boolean isValidPhoneInternational(String phone) {
    if (phone == null || phone.trim().isEmpty()) {
      return false;
    }
    phone = phone.replaceAll("[\\s\\-()]", "");

    return PHONE_INTERNATIONAL_PATTERN.matcher(phone).matches();
  }

  public static boolean isStrongPassword(String password) {
    if (password == null || password.trim().isEmpty()) {
      return false;
    }
    return PASSWORD_STRONG_PATTERN.matcher(password).matches();
  }
}
