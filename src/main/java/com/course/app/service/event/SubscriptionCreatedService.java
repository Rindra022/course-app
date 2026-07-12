package com.course.app.service.event;

import com.course.app.endpoint.event.model.SubscriptionCreated;
import com.course.app.mail.Email;
import com.course.app.mail.Mailer;
import com.course.app.service.CourseService;
import com.course.app.service.TicketService;
import com.course.app.service.UserService;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
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
  private final TicketService ticketService;

  @Override
  @SneakyThrows
  public void accept(SubscriptionCreated event) {
    var subscription = event.getSubscription();
    var user = userService.getById(subscription.userId());
    var course = courseService.getById(subscription.courseId());

    var ticketUrl =
        ticketService.generateAndUploadTicket(subscription.id(), user.userName(), course.title());

    var subject = "Subscription confirmation: %s".formatted(course.title());
    var htmlBody =
        """
        <html>
          <body>
            <p>Dear %s,</p>
            <p>Your subscription has been confirmed. You now have full access to your course.</p>
            <p><a href="%s">Download your ticket (link valid 24h)</a></p>
            <p>Thank you for joining us!</p>
            <p>Best regards,</p>
            <p>The Team</p>
          </body>
        </html>
        """
            .formatted(user.userName(), ticketUrl);

    var email =
        new Email(
            new InternetAddress(user.email()), List.of(), List.of(), subject, htmlBody, List.of());
    mailer.accept(email);
  }
}
