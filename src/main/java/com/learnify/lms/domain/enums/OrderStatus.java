package com.learnify.lms.domain.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
  PENDING,
  PROCESSING,
  COMPLETED,
  CANCELLED,
  REFUNDED
}
