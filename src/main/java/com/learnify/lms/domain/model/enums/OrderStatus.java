package com.learnify.lms.domain.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
  PENDING,
  PROCESSING,
  COMPLETED,
  CANCELLED,
  REFUNDED
}

