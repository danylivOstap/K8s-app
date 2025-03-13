package com.od.author_persistence.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.od.author_persistence.model.Author;
import com.od.author_persistence.services.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorsController {
    private final AuthorService authorService;

    @GetMapping(path="/authors")
    public Page<Author> listAuthors(final Pageable pageable) {
        return authorService.listAuthors(pageable);
    }
}
