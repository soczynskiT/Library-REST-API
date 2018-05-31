package com.kodilla.library.service;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.exceptions.BookNotFoundException;
import com.kodilla.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookCopiesService {

    @Autowired
    BookCopyRepository bookCopyRepository;

    @Autowired
    BooksService booksService;

    public BookCopy saveBookCopy(final BookCopy bookCopy, final Long bookId) throws Exception {
        bookCopy.setBook(booksService.getBookOfId(bookId));
        return bookCopyRepository.save(bookCopy);
    }

    public BookCopy updateBookCopy(final BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public List<BookCopy> getAllCopiesByBookId(final Long id) throws Exception {
        final Book book = booksService.getBookOfId(id);
        return bookCopyRepository.findByBook_Id(book.getId()).orElseGet(ArrayList::new);
    }

    public List<BookCopy> getAllCopiesWithBookIdAndStatus(Long bookId, BookCopyStatus bookCopyStatus) throws Exception {
        final Book book = booksService.getBookOfId(bookId);
        return bookCopyRepository.findByBook_IdAndStatus(bookId, bookCopyStatus).orElseGet(ArrayList::new);
    }
}
