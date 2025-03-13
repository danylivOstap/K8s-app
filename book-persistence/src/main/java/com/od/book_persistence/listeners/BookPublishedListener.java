package com.od.book_persistence.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.od.book_persistence.exceptions.InvalidMessageException;
import com.od.book_persistence.model.Book;
import com.od.book_persistence.model.Notification;
import com.od.book_persistence.service.BookService;
import com.od.book_persistence.service.NotificationService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("production")
@RequiredArgsConstructor
public class BookPublishedListener {
  private final ObjectMapper objectMapper;
  private final BookService bookService;
  private final NotificationService notificationService;


  @KafkaListener(topics = "books.published")
  public String listens(final String in) {
    log.info("Received Book: {}", in);

    try {
      final Map<String, Object> payload = readJsonAsMap(in);

      final Book book = bookFromPayload(payload);
      final Book savedBook = bookService.save(book);

      final String message = String.format(
          "Book '%s' [%s] persisted!",
          savedBook.getTitle(),
          savedBook.getIsbn()
      );

      notificationService.publishNotification(
          Notification.builder()
              .message(message)
              .timestamp(LocalDateTime.now())
              .service("book-persistence")
              .build()
      );
    } catch (final InvalidMessageException ex) {
      log.error("Invalid message received: {}", in);
    }

    return in;
  }

  private Book bookFromPayload(final Map<String, Object> payload) {
    final Integer authorId = (Integer)((HashMap<String, Object>)payload.get("author")).get("id"); /* <- Don't do this in prod!!! :| */
    return Book.builder()
        .isbn(payload.get("isbn").toString())
        .title(payload.get("title").toString())
        .author(authorId.longValue())
        .build();
  }

  private Map<String, Object> readJsonAsMap(String json) {
    try {
      final TypeReference<HashMap<String, Object>> typeRef =
          new TypeReference<HashMap<String, Object>>() {};
      return objectMapper.readValue(json, typeRef);
    } catch (JsonProcessingException ex) {
      throw new InvalidMessageException();
    }
  }
}
