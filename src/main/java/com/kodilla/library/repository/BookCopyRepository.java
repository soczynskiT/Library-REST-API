package com.kodilla.library.repository;

import com.kodilla.library.domain.BookCopy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {

    @Override
    BookCopy save(BookCopy bookCopy);

    @Override
    List<BookCopy> findAll();

    @Query(nativeQuery = true)
    List<BookCopy> retrieveAvailableCopiesByBookId(@Param("BOOK_ID") final Long id);
}
