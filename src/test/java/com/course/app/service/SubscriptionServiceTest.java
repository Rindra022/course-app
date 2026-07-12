package com.course.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.course.app.endpoint.event.EventProducer;
import com.course.app.endpoint.event.model.SubscriptionCreated;
import com.course.app.endpoint.rest.controller.dto.SubscriptionRequest;
import com.course.app.mapper.SubscriptionMapper;
import com.course.app.model.Subscription;
import com.course.app.model.SubscriptionStatus;
import com.course.app.repository.SubscriptionRepository;
import com.course.app.repository.model.JSubscription;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SubscriptionServiceTest {

  private final SubscriptionRepository repository = mock(SubscriptionRepository.class);
  private final SubscriptionMapper mapper = mock(SubscriptionMapper.class);
  private final EventProducer<SubscriptionCreated> eventProducer = mock(EventProducer.class);
  private final SubscriptionService service =
      new SubscriptionService(repository, mapper, eventProducer);

  @Test
  void create_should_save_subscription_and_produce_event() {
    var courseId = UUID.randomUUID();
    var userId = UUID.randomUUID();
    var request = SubscriptionRequest.builder().userId(userId).build();
    var jEntity = new JSubscription();
    var model =
        Subscription.builder()
            .id(UUID.randomUUID())
            .createdAt(Instant.now())
            .status(SubscriptionStatus.ACTIVE)
            .courseId(courseId)
            .userId(userId)
            .build();

    when(mapper.toEntity(courseId, request)).thenReturn(jEntity);
    when(repository.save(jEntity)).thenReturn(jEntity);
    when(mapper.toModel(jEntity)).thenReturn(model);

    var result = service.create(courseId, request);

    assertThat(result).isEqualTo(model);
    verify(repository).save(jEntity);
    verify(eventProducer).accept(List.of(new SubscriptionCreated(model)));
  }
}
