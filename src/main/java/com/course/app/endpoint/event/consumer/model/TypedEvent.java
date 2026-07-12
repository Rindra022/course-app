package com.course.app.endpoint.event.consumer.model;

import com.course.app.PojaGenerated;
import com.course.app.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
