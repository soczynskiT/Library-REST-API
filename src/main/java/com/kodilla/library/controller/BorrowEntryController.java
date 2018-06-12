package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dtos.BorrowEntryDto;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import com.kodilla.library.mapper.BorrowEntryMapper;
import com.kodilla.library.mapper.LibraryUserMapper;
import com.kodilla.library.service.BorrowEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/books")
public class BorrowEntryController {

    private final LibraryUserMapper libraryUserMapper;
    private final BorrowEntryMapper borrowEntryMapper;
    private final BorrowEntryService borrowEntryService;

    @Autowired
    public BorrowEntryController(final LibraryUserMapper libraryUserMapper, final BorrowEntryMapper borrowEntryMapper,
                                 final BorrowEntryService borrowEntryService) {
        this.libraryUserMapper = libraryUserMapper;
        this.borrowEntryMapper = borrowEntryMapper;
        this.borrowEntryService = borrowEntryService;
    }

    @PostMapping(value = "/{id}/borrow", consumes = APPLICATION_JSON_VALUE)
    public BorrowEntryDto borrowBook(@RequestBody final LibraryUserDto libraryUserDto,
                                     @PathVariable("id") final Long bookId) {
        final BorrowEntry newBorrowEntry = borrowEntryService.createBorrowEntry(libraryUserMapper.mapToLibraryUser(libraryUserDto), bookId);
        return borrowEntryMapper.mapToBorrowEntryDto(newBorrowEntry);
    }

    @PutMapping(value = "/return", consumes = APPLICATION_JSON_VALUE)
    public BorrowEntryDto returnBook(@RequestBody final BorrowEntryDto borrowEntryDto) {
        final BorrowEntry updatedEntry = borrowEntryService.returnBook(borrowEntryMapper.mapToBorrowEntry(borrowEntryDto));
        return borrowEntryMapper.mapToBorrowEntryDto(updatedEntry);
    }
}
