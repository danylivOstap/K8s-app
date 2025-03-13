package com.od.book_persistence.repositories;

import com.od.book_persistence.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

}
