package com.learnify.lms.model;

import com.learnify.lms.model.enumeration.EOrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends AbstractEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "order_number", nullable = false, unique = true, length = 50)
  private String orderNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EOrderStatus status;

  @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal totalAmount;

  @Column(name = "discount_amount", precision = 10, scale = 2)
  @Builder.Default
  private BigDecimal discountAmount = BigDecimal.ZERO;

  @Column(name = "final_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal finalAmount;

  @Column(length = 10)
  @Builder.Default
  private String currency = "VND";

  @Column(name = "completed_at")
  private LocalDateTime completedAt;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  @Builder.Default
  private Set<OrderItem> items = new HashSet<>();

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  @Builder.Default
  private Set<Payment> payments = new HashSet<>();
}
