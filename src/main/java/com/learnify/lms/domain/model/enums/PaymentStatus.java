package com.learnify.lms.domain.model.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
  PENDING,
  COMPLETED,
  FAILED,
  REFUNDED
}
