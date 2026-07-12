package com.course.app.service;

import com.course.app.endpoint.event.EventProducer;
import com.course.app.endpoint.event.model.SubscriptionCreated;
import com.course.app.endpoint.rest.controller.dto.SubscriptionRequest;
import com.course.app.mapper.SubscriptionMapper;
import com.course.app.model.Subscription;
import com.course.app.repository.SubscriptionRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
  private final SubscriptionRepository repository;
  private final SubscriptionMapper mapper;
  private final EventProducer<SubscriptionCreated> eventProducer;

  public Subscription create(UUID courseId, SubscriptionRequest request) {
    var entity = mapper.toEntity(courseId, request);
    var saved = mapper.toModel(repository.save(entity));
    eventProducer.accept(List.of(new SubscriptionCreated(saved)));
    return saved;
  }
}
