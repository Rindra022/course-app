package com.course.app.service.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.course.app.endpoint.event.model.SubscriptionCreated;
import com.course.app.mail.Email;
import com.course.app.mail.Mailer;
import com.course.app.model.Course;
import com.course.app.model.Subscription;
import com.course.app.model.SubscriptionStatus;
import com.course.app.model.User;
import com.course.app.service.CourseService;
import com.course.app.service.UserService;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SubscriptionCreatedServiceTest {

  private final Mailer mailer = mock(Mailer.class);
  private final UserService userService = mock(UserService.class);
  private final CourseService courseService = mock(CourseService.class);
  private final SubscriptionCreatedService service =
      new SubscriptionCreatedService(mailer, userService, courseService);

  @Test
  void accept_should_send_confirmation_email_to_the_subscribed_user() {
    var userId = UUID.randomUUID();
    var courseId = UUID.randomUUID();
    var user = User.builder().id(userId).userName("jdoe").email("jdoe@example.com").build();
    var course = Course.builder().id(courseId).title("Spring Boot 101").build();
    var subscription =
        Subscription.builder()
            .id(UUID.randomUUID())
            .createdAt(Instant.now())
            .status(SubscriptionStatus.ACTIVE)
            .userId(userId)
            .courseId(courseId)
            .build();

    when(userService.getById(userId)).thenReturn(user);
    when(courseService.getById(courseId)).thenReturn(course);

    service.accept(new SubscriptionCreated(subscription));

    verify(mailer).accept(any(Email.class));
  }
}
