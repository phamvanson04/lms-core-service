package com.learnify.lms.domain.model;

import com.learnify.lms.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

@Entity
@Table(
    name = "lesson_progress",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id", "lesson_id"})})
@Data
@EqualsAndHashCode(callSuper = true)
@Where(clause = "deleted_at IS NULL")
public class LessonProgress extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private User student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id", nullable = false)
  private Lesson lesson;

  @Column(name = "is_completed")
  private Boolean isCompleted = false;

  @Column(name = "last_watched_second")
  private Integer lastWatchedSecond;
}

