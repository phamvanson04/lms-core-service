package com.learnify.lms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment extends AbstractEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id", nullable = false)
  private Lesson lesson;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "TEXT")
  private String instructions;

  @Column(name = "attachment_url")
  private String attachmentUrl;

  @Column(name = "max_score", nullable = false)
  private Integer maxScore;

  @Column(name = "passing_score", nullable = false)
  private Integer passingScore;

  @Column(name = "due_date")
  private LocalDateTime dueDate;

  @Column(name = "allow_late_submission", nullable = false)
  private Boolean allowLateSubmission;

  @Column(name = "is_published", nullable = false)
  private Boolean isPublished;

  @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
  private Set<Submission> submissions;

  @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
  private Set<Question> questions;
}
