package com.learnify.lms.model.enumeration;

import lombok.Getter;

@Getter
public enum EOrderStatus {
  PENDING,
  PROCESSING,
  COMPLETED,
  CANCELLED,
  REFUNDED
}
