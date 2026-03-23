package com.learnify.lms.domain.model;

import com.learnify.lms.common.base.BaseEntity;
import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "course_sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSection extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(name = "order_index", nullable = false)
  private Integer orderIndex;

  @Column(name = "lesson_count", nullable = false)
  private Integer lessonCount;

  @Column(name = "total_duration", nullable = false)
  private Integer totalDuration;

  @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
  private Set<Lesson> lessons;
}
