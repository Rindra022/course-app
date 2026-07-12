package com.course.app.service.event;

import com.course.app.endpoint.event.model.SubscriptionCreated;
import com.course.app.mail.Email;
import com.course.app.mail.Mailer;
import com.course.app.service.CourseService;
import com.course.app.service.UserService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionCreatedService implements Consumer<SubscriptionCreated> {
  private final Mailer mailer;
  private final UserService userService;
  private final CourseService courseService;

  @Override
  @SneakyThrows
  public void accept(SubscriptionCreated event) {
    sendCourseConfirmationEmailToUser(
        event.getSubscription().userId(), event.getSubscription().courseId());
  }

  private void sendCourseConfirmationEmailToUser(UUID userId, UUID courseId)
      throws AddressException {
    var user = userService.getById(userId);
    var course = courseService.getById(courseId);
    var to = user.email();
    var subject = "Subscription confirmation: %s".formatted(course.title());
    var htmlBody =
        """
        <html>
          <body>
            <p>Dear %s,</p>
            <p>Your subscription has been confirmed. You now have full access to your course.</p>
            <p>Thank you for joining us!</p>
            <p>Best regards,</p>
            <p>The Team</p>
          </body>
        </html>
        """
            .formatted(user.userName());
    var email =
        new Email(new InternetAddress(to), List.of(), List.of(), subject, htmlBody, List.of());
    mailer.accept(email);
  }
}
