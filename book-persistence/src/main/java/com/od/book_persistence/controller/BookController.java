package com.od.book_persistence.controller;

import com.od.book_persistence.model.Book;
import com.od.book_persistence.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;

  @GetMapping
  public Page<Book> getAll(final Pageable pageable) {
    return bookService.getAll(pageable);
  }
}
