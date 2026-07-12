package com.course.app.repository;

import com.course.app.repository.model.JSubscription;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<JSubscription, UUID> {}
