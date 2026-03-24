package com.learnify.lms.domain.model;

import com.learnify.lms.domain.enums.NotificationChannel;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String message;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private NotificationChannel channel;

  @Column(name = "read_at")
  private LocalDateTime readAt;

  @Column(name = "sent_at")
  private LocalDateTime sentAt;

  @Column(columnDefinition = "TEXT")
  private String metadata;
}
