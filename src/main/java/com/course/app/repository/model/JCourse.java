package com.course.app.repository.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "course")
public class JCourse {
  @Id @GeneratedValue private UUID id;

  @Column(nullable = false, unique = true)
  private String title;

  private Instant startDate;
  private Instant endDate;
}
