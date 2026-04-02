package com.learnify.lms.model;

import com.learnify.lms.model.enumeration.EPaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends AbstractEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(length = 10)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EPaymentStatus status;

  @Column(name = "payment_method", length = 30)
  private String paymentMethod;

  @Column(name = "transaction_id", unique = true, length = 100)
  private String transactionId;

  @Column(name = "payment_gateway", length = 50)
  private String paymentGateway;

  @Column(columnDefinition = "TEXT")
  private String metadata;

  @Column(name = "paid_at")
  private LocalDateTime paidAt;
}
