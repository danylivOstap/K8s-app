package com.od.book_publisher.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.od.book_publisher.config.KafkaConfigProps;
import com.od.book_publisher.exceptions.BookPublishException;
import com.od.book_publisher.model.Book;
import com.od.book_publisher.service.BookPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookPublisherServiceImpl implements BookPublisherService {
  private final ObjectMapper objectMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final KafkaConfigProps kafkaConfigProps;

  @Override
  public void publish(final Book book) {
    try {
      final String payload = objectMapper.writeValueAsString(book);
      kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
    } catch (final JsonProcessingException ex) {
      throw new BookPublishException("Unable to publish book", ex, book);
    }
  }
}
