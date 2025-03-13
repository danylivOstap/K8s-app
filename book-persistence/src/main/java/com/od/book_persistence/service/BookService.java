package com.od.book_persistence.service;

import com.od.book_persistence.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
  Book save(Book book);

  Page<Book> getAll(Pageable pageable);
}
