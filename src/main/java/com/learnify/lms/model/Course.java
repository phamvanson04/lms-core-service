package com.learnify.lms.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends AbstractEntity {
  @Column(nullable = false, length = 200)
  private String title;

  @Column(length = 100)
  private String slug;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "TEXT")
  private String requirements;

  @Column(columnDefinition = "TEXT")
  private String objectives;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "instructor_id", nullable = false)
  private User instructor;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "discount_price", precision = 10, scale = 2)
  private BigDecimal discountPrice;

  @Column(name = "thumbnail_url")
  private String thumbnailUrl;

  @Column(name = "preview_video_url")
  private String previewVideoUrl;

  @Column(length = 10)
  private String language;

  @Column(name = "total_duration", nullable = false)
  private Integer totalDuration;

  @Column(name = "total_lessons", nullable = false)
  private Integer totalLessons;

  @Column(name = "total_students", nullable = false)
  private Integer totalStudents;

  @Column(name = "published_at")
  private LocalDateTime publishedAt;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private Set<CourseSection> sections;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  private Set<Enrollment> enrollments;
}
