// UserService
package com.course.app.service;

import com.course.app.mapper.CourseMapper;
import com.course.app.model.Course;
import com.course.app.repository.CourseRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {
  private final CourseMapper mapper;
  private final CourseRepository repository;

  public Course getById(UUID id) {
    return mapper.toModel(
        repository.findById(id).orElseThrow(() -> new RuntimeException("Course not found")));
  }
}
