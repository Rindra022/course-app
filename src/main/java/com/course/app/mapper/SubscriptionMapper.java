package com.course.app.mapper;

import static com.course.app.model.SubscriptionStatus.ACTIVE;

import com.course.app.endpoint.rest.controller.dto.SubscriptionRequest;
import com.course.app.model.Subscription;
import com.course.app.repository.model.JSubscription;
import com.course.app.service.CourseService;
import com.course.app.service.UserService;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SubscriptionMapper {
  private final UserService userService;
  private final CourseService courseService;
  private final UserMapper userMapper;
  private final CourseMapper courseMapper;

  public Subscription toModel(JSubscription entity) {
    return Subscription.builder()
        .id(entity.getId())
        .createdAt(entity.getCreatedAt())
        .status(entity.getStatus())
        .courseId(entity.getCourse().getId())
        .userId(entity.getUser().getId())
        .build();
  }

  public JSubscription toEntity(UUID courseId, SubscriptionRequest request) {
    var user = userService.getById(request.userId());
    var course = courseService.getById(courseId);
    return JSubscription.builder()
        .id(UUID.randomUUID())
        .createdAt(Instant.now())
        .status(ACTIVE)
        .user(userMapper.toEntity(user))
        .course(courseMapper.toEntity(course))
        .build();
  }
}
