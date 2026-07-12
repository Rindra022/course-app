package com.course.app.service;

import com.course.app.endpoint.rest.controller.dto.SubscriptionRequest;
import com.course.app.mapper.SubscriptionMapper;
import com.course.app.model.Subscription;
import com.course.app.repository.SubscriptionRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository repository;
  private final SubscriptionMapper mapper;

  public Subscription create(UUID courseId, SubscriptionRequest request) {
    var entity = mapper.toEntity(courseId, request);
    return mapper.toModel(repository.save(entity));
  }
}
