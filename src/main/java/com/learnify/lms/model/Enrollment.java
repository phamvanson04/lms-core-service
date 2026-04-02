package com.learnify.lms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(
    name = "enrollments",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "course_id"})})
@Getter
@Setter
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment extends AbstractEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private User student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(name = "progress_percentage", nullable = false)
  private Integer progressPercentage;

  @Column(name = "completed_lessons", nullable = false)
  private Integer completedLessons;

  @Column(name = "total_lessons", nullable = false)
  private Integer totalLessons;

  @Column(name = "certificate_url")
  private String certificateUrl;

  @Column(name = "completed_at")
  private LocalDateTime completedAt;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;
}
