package com.kodilla.library.mapper;

import com.kodilla.library.domain.BorrowEntry;
import com.kodilla.library.domain.dtos.BorrowEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BorrowEntryMapper {
    private final LibraryUserMapper libraryUserMapper;
    private final BookCopyMapper bookCopyMapper;

    public BorrowEntry mapToBorrowEntry(final BorrowEntryDto borrowEntryDto) {
        return new BorrowEntry(
                borrowEntryDto.getId(),
                libraryUserMapper.mapToLibraryUser(borrowEntryDto.getLibraryUserDto()),
                bookCopyMapper.mapToBookCopy(borrowEntryDto.getBookCopyDto()),
                borrowEntryDto.getBorrowStart(),
                borrowEntryDto.getBorrowEnd(),
                borrowEntryDto.getBorrowStatus());
    }

    public BorrowEntryDto mapToBorrowEntryDto(final BorrowEntry borrowEntry) {
        return new BorrowEntryDto(
                borrowEntry.getId(),
                libraryUserMapper.mapToLibraryUserDto(borrowEntry.getLibraryUser()),
                bookCopyMapper.mapToBookCopyDto(borrowEntry.getBookCopy()),
                borrowEntry.getBorrowStart(),
                borrowEntry.getBorrowEnd(),
                borrowEntry.getBorrowStatus());
    }

}
