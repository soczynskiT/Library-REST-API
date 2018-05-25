package com.kodilla.library.repository;

import com.kodilla.library.domain.BorrowEntry;
import org.springframework.data.repository.CrudRepository;

public interface BorrowEntryRepository extends CrudRepository<BorrowEntry, Long> {

    @Override
    BorrowEntry save(BorrowEntry borrowEntry);

}
