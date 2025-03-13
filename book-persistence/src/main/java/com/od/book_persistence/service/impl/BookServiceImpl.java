package com.od.book_persistence.service.impl;

import com.od.book_persistence.model.Book;
import com.od.book_persistence.repositories.BookRepository;
import com.od.book_persistence.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;

  @Override
  public Book save(final Book book) {
    return bookRepository.save(book);
  }

  @Override
  public Page<Book> getAll(final Pageable pageable) {
    return bookRepository.findAll(pageable);
  }
}
