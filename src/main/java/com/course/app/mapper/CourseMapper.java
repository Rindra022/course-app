package com.course.app.mapper;

import com.course.app.model.Course;
import com.course.app.repository.model.JCourse;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
  public Course toModel(JCourse entity) {
    return Course.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .startDate(entity.getStartDate())
        .endDate(entity.getEndDate())
        .build();
  }

  public JCourse toEntity(Course model) {
    return JCourse.builder()
        .id(model.id())
        .title(model.title())
        .startDate(model.startDate())
        .endDate(model.endDate())
        .build();
  }
}
