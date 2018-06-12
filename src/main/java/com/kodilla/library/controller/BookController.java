package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dtos.BookDto;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/books")
public class BookController {
    private final BookMapper bookMapper;
    private final BookService bookService;

    @Autowired
    public BookController(final BookMapper bookMapper, final BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getBooks() {
        final List<Book> books = bookService.getAllBooks();
        return bookMapper.mapToBookDtoList(books);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody final BookDto bookDto) {
        final Book createdBook = bookMapper.mapToBook(bookDto);
        final Book savedBook = bookService.saveBook(createdBook);
        return bookMapper.mapToBookDto(savedBook);
    }

    @PutMapping
    public BookDto updateBook(@RequestBody final BookDto bookDto) {
        final Book createdBook = bookMapper.mapToBook(bookDto);
        final Book updatedBook = bookService.saveBook(createdBook);
        return bookMapper.mapToBookDto(updatedBook);
    }
}
