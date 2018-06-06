package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookCopyService {
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private BookService bookService;

    public BookCopy saveBookCopy(final BookCopy bookCopy, final Long bookId) {
        bookCopy.setBook(bookService.getBookOfId(bookId));
        return bookCopyRepository.save(bookCopy);
    }

    BookCopy updateBookCopy(final BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public List<BookCopy> getAllCopiesByBookId(final Long id) {
        final Book book = bookService.getBookOfId(id);
        return bookCopyRepository.findByBook_Id(book.getId()).orElseGet(ArrayList::new);
    }

    public List<BookCopy> getAllCopiesWithBookIdAndStatus(final Long bookId, final BookCopyStatus bookCopyStatus) {
        validateIfBookExist(bookId);
        return bookCopyRepository.findByBook_IdAndStatus(bookId, bookCopyStatus).orElseGet(ArrayList::new);
    }

    private boolean validateIfBookExist(final Long bookId) {
        final Optional<Book> bookUnderCheck = Optional.ofNullable(bookService.getBookOfId(bookId));
        return bookUnderCheck.isPresent();
    }
}
