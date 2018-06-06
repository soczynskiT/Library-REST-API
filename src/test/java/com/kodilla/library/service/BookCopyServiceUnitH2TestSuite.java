package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.exceptions.BookNotFoundException;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookCopyServiceUnitH2TestSuite {
    @Autowired
    BookCopyService bookCopyService;
    @Autowired
    BookCopyRepository bookCopyRepository;
    @Autowired
    BookRepository bookRepository;

    @Test
    public void shouldSaveBookCopyToDb() {
        //Given
        final Book book = new Book("testTitle", "testAuthor", 999);
        bookRepository.save(book);
        final BookCopy bookCopy = new BookCopy(new Book(), BookCopyStatus.LOST);

        //When
        bookCopyService.saveBookCopy(bookCopy, book.getId());
        final Long bookCopyId = bookCopy.getId();
        final BookCopy savedBookCopy = bookCopyRepository.findOne(bookCopyId);

        //Then
        assertEquals(bookCopyId, savedBookCopy.getId());
        assertEquals(book.getId(), savedBookCopy.getBook().getId());
        assertEquals(BookCopyStatus.LOST, savedBookCopy.getStatus());
    }

    @Test
    public void shouldThrowBookNotFoundExceptionForSavingCopyOfNotExistingBook() {
        //Given
        final BookCopy bookCopy = new BookCopy(new Book(), BookCopyStatus.LOST);
        final Long notExistingBookId = (long) -1;

        //When & Then
        assertThatThrownBy(() -> bookCopyService.saveBookCopy(bookCopy, notExistingBookId))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void updateBookCopy() {
        //Given
        final Book book = new Book("testTitle", "testAuthor", 999);
        bookRepository.save(book);
        final BookCopy bookCopy = new BookCopy(new Book(), BookCopyStatus.LOST);
        bookCopyService.saveBookCopy(bookCopy, book.getId());

        final Long bookCopyId = bookCopy.getId();

        //When
        bookCopy.setStatus(BookCopyStatus.DESTROYED);
        BookCopy updatedBookCopy = bookCopyService.updateBookCopy(bookCopy);

        //Then
        assertEquals(bookCopyId, updatedBookCopy.getId());
        assertEquals(book.getId(), updatedBookCopy.getBook().getId());
        assertEquals(BookCopyStatus.DESTROYED, updatedBookCopy.getStatus());
    }

    @Test
    public void getAllCopiesByBookId() {
        final Book book = new Book("testTitle", "testAuthor", 999);
        bookRepository.save(book);
        final Long savedBookId = book.getId();

        final BookCopy bookCopy = new BookCopy(new Book(), BookCopyStatus.LOST);
        final BookCopy bookCopy2 = new BookCopy(new Book(), BookCopyStatus.AVAILABLE);
        final BookCopy bookCopy3 = new BookCopy(new Book(), BookCopyStatus.DESTROYED);
        bookCopyService.saveBookCopy(bookCopy, savedBookId);
        bookCopyService.saveBookCopy(bookCopy2, savedBookId);
        bookCopyService.saveBookCopy(bookCopy3, savedBookId);

        //When
        final List<BookCopy> foundedBookCopies = bookCopyService.getAllCopiesByBookId(savedBookId);

        //Then
        assertEquals(3, foundedBookCopies.size());
    }

    @Test
    public void shouldThrowExceptionForFindingCopiesByNotExistingBookId() {
        //Given
        final Long notExistingBookId = (long) -1;

        //When & Then
        assertThatThrownBy(() -> bookCopyService.getAllCopiesByBookId(notExistingBookId))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void shouldReturnAllCopiesWithBookIdAndStatus() {
        //Given
        final Book book = new Book("testTitle", "testAuthor", 999);
        bookRepository.save(book);
        final Long savedBookId = book.getId();

        final BookCopy bookCopy = new BookCopy(new Book(), BookCopyStatus.LOST);
        final BookCopy bookCopy2 = new BookCopy(new Book(), BookCopyStatus.LOST);
        final BookCopy bookCopy3 = new BookCopy(new Book(), BookCopyStatus.DESTROYED);
        bookCopyService.saveBookCopy(bookCopy, savedBookId);
        bookCopyService.saveBookCopy(bookCopy2, savedBookId);
        bookCopyService.saveBookCopy(bookCopy3, savedBookId);

        //When
        final List<BookCopy> foundedBookCopies = bookCopyService.getAllCopiesWithBookIdAndStatus(savedBookId, BookCopyStatus.LOST);

        //Then
        assertEquals(2, foundedBookCopies.size());
    }

    @Test
    public void shouldThrowExceptionForCopiesWithAnyStatusAndNotExistingBookId() {
        //Given
        final Long notExistingBookId = (long) -1;

        ///When & Then
        assertThatThrownBy(() -> bookCopyService.getAllCopiesWithBookIdAndStatus(notExistingBookId, BookCopyStatus.AVAILABLE))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void shouldReturnEmptyListForExistingBookAndNotMatchingStatus() {
        //Given
        final Book book = new Book("testTitle", "testAuthor", 999);
        bookRepository.save(book);
        final Long savedBookId = book.getId();

        final BookCopy bookCopy = new BookCopy(new Book(), BookCopyStatus.LOST);
        final BookCopy bookCopy2 = new BookCopy(new Book(), BookCopyStatus.AVAILABLE);
        final BookCopy bookCopy3 = new BookCopy(new Book(), BookCopyStatus.DESTROYED);
        bookCopyService.saveBookCopy(bookCopy, savedBookId);
        bookCopyService.saveBookCopy(bookCopy2, savedBookId);
        bookCopyService.saveBookCopy(bookCopy3, savedBookId);

        //When
        final List<BookCopy> foundedBookCopies = bookCopyService.getAllCopiesWithBookIdAndStatus(savedBookId, BookCopyStatus.BORROWED);

        //Then
        assertEquals(0, foundedBookCopies.size());
    }
}