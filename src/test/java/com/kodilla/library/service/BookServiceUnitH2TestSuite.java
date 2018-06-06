package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.exceptions.BookNotFoundException;
import com.kodilla.library.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceUnitH2TestSuite {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;


    @Test
    public void shouldSaveBookToDb() {
        //Given
        final Book book = new Book("testTitle", "testAuthor", 999);

        //When
        bookService.saveBook(book);
        final Long id = book.getId();
        final Optional<Book> savedBook = bookRepository.findById(id);

        //Then
        assertTrue(savedBook.isPresent());
        assertEquals(id, savedBook.get().getId());
        assertEquals("testTitle", savedBook.get().getTitle());
        assertEquals("testAuthor", savedBook.get().getAuthor());
        assertEquals(999, savedBook.get().getPublicationYear());

    }

    @Test
    public void shouldFetchAllBooks() {
        //Given
        final Book book = new Book("testTitle", "testAuthor", 999);
        final Book book2 = new Book("testTitle2", "testAuthor2", 1000);
        bookRepository.save(book);
        bookRepository.save(book2);

        //When
        final List<Book> books = bookService.getAllBooks();

        //Then
        assertEquals(2, books.size());
    }

    @Test
    public void shouldReturnEmptyList() {
        //Given & When
        final List<Book> books = bookService.getAllBooks();

        //Then
        assertEquals(0, books.size());
    }

    @Test
    public void shouldReturnBookOfRequestedId() {
        //Given
        final Book book = new Book("testTitle", "testAuthor", 999);
        bookRepository.save(book);
        final Long bookId = book.getId();

        //When
        final Book foundedBook = bookService.getBookOfId(bookId);

        //Then
        assertEquals(book.getId(), foundedBook.getId());
        assertEquals("testAuthor", foundedBook.getAuthor());
        assertEquals(999, foundedBook.getPublicationYear());

    }

    @Test
    public void shouldThrowBookNotFoundExceptionForNotExistingBook() {
        //Given
        final Long notExistingBookId = (long) -1;

        //When & //Then
        assertThatThrownBy(() -> bookService.getBookOfId(notExistingBookId))
                .isInstanceOf(BookNotFoundException.class);

    }
}