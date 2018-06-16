package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.exceptions.BookNotFoundException;
import com.kodilla.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Book saveBook(final Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    Book getBookOfId(final Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }
}
