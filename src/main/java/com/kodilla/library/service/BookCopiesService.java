package com.kodilla.library.service;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookCopiesService {

    @Autowired
    BookCopyRepository bookCopyRepository;

    public BookCopy saveBookCopy(final BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public Optional<List<BookCopy>> getAllCopiesByBookId(final Long id) {
        return bookCopyRepository.findByBook_Id(id);
    }

    public Optional<List<BookCopy>> getAllAvailableCopiesWithBookId(Long bookId, BookCopyStatus bookCopyStatus) {
        return bookCopyRepository.findByBook_IdAndStatus(bookId, bookCopyStatus);
    }
}
