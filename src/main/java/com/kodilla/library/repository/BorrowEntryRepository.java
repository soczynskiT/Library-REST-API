package com.kodilla.library.repository;

import com.kodilla.library.domain.BorrowEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BorrowEntryRepository extends CrudRepository<BorrowEntry, Long> {

    @Override
    BorrowEntry save(BorrowEntry borrowEntry);

    Optional<BorrowEntry> findById(Long entryId);

}
