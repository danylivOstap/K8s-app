package com.od.book_publisher.repository;

import com.od.book_publisher.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
