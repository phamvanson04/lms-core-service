package com.learnify.lms.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
@Builder
public class Question extends AbstractEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignment_id", nullable = false)
  private Assignment assignment;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "options", columnDefinition = "TEXT")
  private String options;

  @Column(name = "correct_answer", columnDefinition = "TEXT")
  private String correctAnswer;

  @Column(name = "explanation", columnDefinition = "TEXT")
  private String explanation;

  @Column(nullable = false)
  private Integer points;

  @Column(name = "order_index", nullable = false)
  private Integer orderIndex;

  @Column(name = "image_url")
  private String imageUrl;
}
