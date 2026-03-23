package com.learnify.lms.common.base;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  protected LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  protected LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public void softDelete() {
    this.deletedAt = LocalDateTime.now();
  }

  public void restore() {
    this.deletedAt = null;
  }

  public boolean isDeleted() {
    return this.deletedAt != null;
  }
}
