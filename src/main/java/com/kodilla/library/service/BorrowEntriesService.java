package com.kodilla.library.service;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BorrowEntry;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.enums.BorrowStatus;
import com.kodilla.library.exceptions.BorrowEntryNotFoundException;
import com.kodilla.library.exceptions.NoAvailableCopiesFoundedException;
import com.kodilla.library.repository.BorrowEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowEntriesService {
    @Autowired
    BorrowEntryRepository borrowEntryRepository;
    @Autowired
    BookCopiesService bookCopiesService;
    @Autowired
    BooksService booksService;


    public BorrowEntry createBorrowEntry(LibraryUser libraryUser, Long bookId) throws Exception {

        final List<BookCopy> availableCopies = bookCopiesService.getAllCopiesWithBookIdAndStatus(bookId, BookCopyStatus.AVAILABLE);
        if (availableCopies.size() > 0) {
            final BookCopy bookCopyToBorrow = availableCopies.get(0);
            final BorrowEntry newBorrowEntry = new BorrowEntry();
            newBorrowEntry.setLibraryUser(libraryUser);
            newBorrowEntry.setBookCopy(bookCopyToBorrow);
            newBorrowEntry.setBorrowStart(Date.valueOf(LocalDate.now()));
            newBorrowEntry.setBorrowEnd(Date.valueOf(LocalDate.now().plusDays(20)));
            newBorrowEntry.setBorrowStatus(BorrowStatus.IN_PROGRESS);
            final BorrowEntry createdBorrowEntry = borrowEntryRepository.save(newBorrowEntry);

            bookCopyToBorrow.setStatus(BookCopyStatus.BORROWED);
            bookCopiesService.updateBookCopy(bookCopyToBorrow);
            return createdBorrowEntry;

        } else {
            throw new NoAvailableCopiesFoundedException();
        }
    }

    public BorrowEntry returnBook(BorrowEntry borrowEntry) throws Exception {
        final BorrowEntry entryToUpdate = borrowEntryRepository.findById(borrowEntry.getId()).orElseThrow(
                BorrowEntryNotFoundException::new);

        entryToUpdate.setBorrowStatus(BorrowStatus.FINISHED);
        entryToUpdate.getBookCopy().setStatus(BookCopyStatus.AVAILABLE);

        return borrowEntryRepository.save(entryToUpdate);
    }
}
