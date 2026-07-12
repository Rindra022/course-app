package com.course.app.endpoint.rest.controller;

import com.course.app.endpoint.rest.controller.dto.SubscriptionRequest;
import com.course.app.model.Subscription;
import com.course.app.service.SubscriptionService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {
  private final SubscriptionService subscriptionService;

  @PostMapping("/{id}/subscribe")
  public Subscription subscribe(@PathVariable UUID id, @RequestBody SubscriptionRequest request) {
    return subscriptionService.create(id, request);
  }
}
