package com.learnify.lms.domain.model;

import com.learnify.lms.domain.enums.DiscountType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends BaseEntity {
  @Enumerated(EnumType.STRING)
  @Column(name = "discount_type", nullable = false, length = 20)
  private DiscountType discountType;

  @Column(name = "coupon_code", nullable = false, unique = true, length = 50)
  private String couponCode;

  @Column(length = 200)
  private String description;

  @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
  private BigDecimal discountValue;

  @Column(name = "min_purchase_amount", precision = 10, scale = 2)
  private BigDecimal minPurchaseAmount;

  @Column(name = "max_discount_amount", precision = 10, scale = 2)
  private BigDecimal maxDiscountAmount;

  @Column(name = "usage_limit")
  private Integer usageLimit;

  @Column(name = "valid_until", nullable = false)
  private LocalDateTime validUntil;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "coupon_courses",
      joinColumns = @JoinColumn(name = "coupon_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<Course> applicableCourses;
}
