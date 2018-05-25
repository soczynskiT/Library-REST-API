package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    @Autowired
    BookRepository bookRepository;

    public Book saveBook(final Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
