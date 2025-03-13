package com.od.author_persistence.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.od.author_persistence.model.Author;

public interface AuthorService {
    Author save(Author book);
    Page<Author> listAuthors(Pageable pageable);
}
