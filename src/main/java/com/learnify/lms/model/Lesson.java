package com.learnify.lms.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson extends AbstractEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "section_id")
  private CourseSection section;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "content_url")
  private String contentUrl;

  @Column(name = "video_duration")
  private Integer videoDuration;

  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
  private Set<Assignment> assignments;
}
