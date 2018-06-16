package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BorrowEntry;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.enums.BorrowStatus;
import com.kodilla.library.exceptions.BorrowEntryNotFoundException;
import com.kodilla.library.exceptions.LibraryUserNotFoundException;
import com.kodilla.library.exceptions.NoAvailableCopiesFoundedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowEntryServiceUnitH2TestSuite {
    @Autowired
    BorrowEntryService borrowEntryService;
    @Autowired
    BookCopyService bookCopyService;
    @Autowired
    BookService bookService;
    @Autowired
    LibraryUserService libraryUserService;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    @Test
    public void createBorrowEntry() throws ParseException {
        //Given
        final Book book = Book.builder()
                .title("testTitle")
                .author("testAuthor")
                .publicationYear(999)
                .build();
        bookService.saveBook(book);
        final Long savedBookId = book.getId();
        final BookCopy bookCopy = BookCopy.builder()
                .book(new Book())
                .status(BookCopyStatus.AVAILABLE)
                .build();
        final BookCopy bookCopy2 = BookCopy.builder()
                .book(new Book())
                .status(BookCopyStatus.LOST)
                .build();
        bookCopyService.saveBookCopy(bookCopy, savedBookId);
        bookCopyService.saveBookCopy(bookCopy2, savedBookId);
        final Long availableBookCopyId = bookCopy.getId();

        final LibraryUser libraryUser = LibraryUser.builder()
                .name("testName")
                .surname("testSurname")
                .joinedDate(DATE_FORMAT.parse("2000/01/01"))
                .build();
        libraryUserService.saveLibraryUser(libraryUser);
        final Long borrowerId = libraryUser.getId();

        //When
        final BorrowEntry newBorrowEntry = borrowEntryService.createBorrowEntry(libraryUser, savedBookId);

        //Then
        assertNotNull(newBorrowEntry.getId());
        assertEquals(borrowerId, newBorrowEntry.getLibraryUser().getId());
        assertEquals(availableBookCopyId, newBorrowEntry.getBookCopy().getId());
        assertNotNull(newBorrowEntry.getBorrowStart());
        assertNotNull(newBorrowEntry.getBorrowEnd());
        assertEquals(BorrowStatus.IN_PROGRESS, newBorrowEntry.getBorrowStatus());
    }

    @Test
    public void shouldThrowExceptionForBorrowTryOfNotExistingUser() throws ParseException {
        //Given
        final LibraryUser notExistingUser = LibraryUser.builder()
                .name("testName")
                .surname("testSurname")
                .joinedDate(DATE_FORMAT.parse("2000/01/01"))
                .build();

        //When & Then
        assertThatThrownBy(() -> borrowEntryService.createBorrowEntry(notExistingUser, 1L))
                .isInstanceOf(LibraryUserNotFoundException.class);
    }

    @Test
    public void shouldThrowExceptionForNoAvailableBookCopiesToBorrow() throws ParseException {
        //Given
        final Book book = Book.builder()
                .title("testTitle")
                .author("testAuthor")
                .publicationYear(999)
                .build();
        bookService.saveBook(book);
        final Long savedBookId = book.getId();
        final BookCopy bookCopy = BookCopy.builder()
                .book(new Book())
                .status(BookCopyStatus.LOST)
                .build();
        final BookCopy bookCopy2 = BookCopy.builder()
                .book(new Book())
                .status(BookCopyStatus.LOST)
                .build();
        bookCopyService.saveBookCopy(bookCopy, savedBookId);
        bookCopyService.saveBookCopy(bookCopy2, savedBookId);

        final LibraryUser libraryUser = LibraryUser.builder()
                .name("testName")
                .surname("testSurname")
                .joinedDate(DATE_FORMAT.parse("2000/01/01"))
                .build();
        libraryUserService.saveLibraryUser(libraryUser);

        //When & Then
        assertThatThrownBy(() -> borrowEntryService.createBorrowEntry(libraryUser, savedBookId))
                .isInstanceOf(NoAvailableCopiesFoundedException.class);
    }

    @Test
    public void shouldProcessBookReturnOfCorrectBorrowEntry() throws ParseException {
        //Given
        //Given
        final Book book = Book.builder()
                .title("testTitle")
                .author("testAuthor")
                .publicationYear(999)
                .build();
        bookService.saveBook(book);
        final Long savedBookId = book.getId();
        final BookCopy bookCopy = BookCopy.builder()
                .book(new Book())
                .status(BookCopyStatus.AVAILABLE)
                .build();
        bookCopyService.saveBookCopy(bookCopy, savedBookId);
        final Long availableBookCopyId = bookCopy.getId();

        final LibraryUser libraryUser = LibraryUser.builder()
                .name("testName")
                .surname("testSurname")
                .joinedDate(DATE_FORMAT.parse("2000/01/01"))
                .build();
        libraryUserService.saveLibraryUser(libraryUser);
        final Long borrowerId = libraryUser.getId();

        final BorrowEntry borrowEntry = borrowEntryService.createBorrowEntry(libraryUser, savedBookId);
        final Long borrowEntryId = borrowEntry.getId();

        //When
        final BorrowEntry updatedBorrowEntry = borrowEntryService.returnBook(borrowEntry);
        final Long updatedBorrowEntryId = updatedBorrowEntry.getId();

        //Then
        assertEquals(borrowEntryId, updatedBorrowEntryId);
        assertEquals(borrowerId, updatedBorrowEntry.getLibraryUser().getId());
        assertEquals(availableBookCopyId, updatedBorrowEntry.getBookCopy().getId());
        assertEquals(BookCopyStatus.AVAILABLE, updatedBorrowEntry.getBookCopy().getStatus());
        assertNotNull(updatedBorrowEntry.getBorrowStart());
        assertNotNull(updatedBorrowEntry.getBorrowEnd());
        assertEquals(BorrowStatus.FINISHED, updatedBorrowEntry.getBorrowStatus());
    }

    @Test
    public void shouldThrowExceptionForIncorrectBorrowEntry() {
        //Given
        final BorrowEntry borrowEntry = new BorrowEntry();

        //When & Then
        assertThatThrownBy(() -> borrowEntryService.returnBook(borrowEntry))
                .isInstanceOf(BorrowEntryNotFoundException.class);
    }
}