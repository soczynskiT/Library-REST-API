package com.kodilla.library.repository;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.enums.BookCopyStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {

    @Override
    BookCopy save(BookCopy bookCopy);

    Optional<List<BookCopy>> findByBook_Id(Long id);

    Optional<List<BookCopy>> findByBook_IdAndStatus(Long bookId, BookCopyStatus status);

}
