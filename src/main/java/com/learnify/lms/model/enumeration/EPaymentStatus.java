package com.learnify.lms.model.enumeration;

import lombok.Getter;

@Getter
public enum EPaymentStatus {
  PENDING,
  COMPLETED,
  FAILED,
  REFUNDED
}
