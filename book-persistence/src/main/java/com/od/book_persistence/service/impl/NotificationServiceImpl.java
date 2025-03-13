package com.od.book_persistence.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.od.book_persistence.config.KafkaConfigProps;
import com.od.book_persistence.exceptions.NotificationPublishException;
import com.od.book_persistence.model.Notification;
import com.od.book_persistence.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
  private final ObjectMapper objectMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final KafkaConfigProps kafkaConfigProps;

  @Override
  public void publishNotification(final Notification notification) {
    try {
      final String payload = objectMapper.writeValueAsString(notification);
      kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
    } catch (JsonProcessingException ex) {
      throw new NotificationPublishException("Unable to publish notification", ex, notification);
    }
  }
}
