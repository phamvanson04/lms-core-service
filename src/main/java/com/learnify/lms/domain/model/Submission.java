package com.learnify.lms.domain.model;

import com.learnify.lms.common.base.BaseEntity;
import com.learnify.lms.domain.model.enums.SubmissionStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignment_id", nullable = false)
  private Assignment assignment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private User student;

  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(name = "attachment_url")
  private String attachmentUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private SubmissionStatus status;

  @Column private Integer score;

  @Column(columnDefinition = "TEXT")
  private String feedback;

  @Column(name = "submitted_at")
  private LocalDateTime submittedAt;

  @Column(name = "graded_at")
  private LocalDateTime gradedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "graded_by")
  private User gradedBy;

  @PrePersist
  protected void onCreate() {
    if (submittedAt == null) {
      submittedAt = LocalDateTime.now();
    }
  }
}
