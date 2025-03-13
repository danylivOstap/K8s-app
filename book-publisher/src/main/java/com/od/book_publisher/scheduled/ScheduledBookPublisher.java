package com.od.book_publisher.scheduled;

import com.od.book_publisher.repository.BookRepository;
import com.od.book_publisher.service.BookPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledBookPublisher {
  private long counter;
  private final BookRepository bookRepository;
  private final BookPublisherService bookPublisherService;

  public ScheduledBookPublisher(final BookPublisherService bookPublisherService,
      final BookRepository bookRepository) {
    resetCounter();
    this.bookPublisherService = bookPublisherService;
    this.bookRepository = bookRepository;
  }

  @Scheduled(cron = "0/20 * * * * *")
  public void publishBook() {
    bookRepository.findById(counter).ifPresentOrElse(book -> {
      counter++;
      bookPublisherService.publish(book);
      log.info("Book '{}' [{}] published.", book.getTitle(), book.getIsbn());
    }, this::resetCounter);
  }

  private void resetCounter() {
    this.counter = 1;
  }
}
