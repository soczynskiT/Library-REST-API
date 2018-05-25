package com.kodilla.library.mapper;

import com.kodilla.library.domain.BorrowEntry;
import com.kodilla.library.domain.dtos.BorrowEntryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BorrowEntryMapper {
    public BorrowEntry mapToBorrowEntry(final BorrowEntryDto borrowEntryDto) {
        return new BorrowEntry(
                borrowEntryDto.getId(),
                borrowEntryDto.getLibraryUser(),
                borrowEntryDto.getBookCopy(),
                borrowEntryDto.getBorrowStart(),
                borrowEntryDto.getBorrowEnd(),
                borrowEntryDto.getBorrowStatus());
    }

    public BorrowEntryDto mapToBorrowEntryDto(final BorrowEntry borrowEntry) {
        return new BorrowEntryDto(
                borrowEntry.getId(),
                borrowEntry.getLibraryUser(),
                borrowEntry.getBookCopy(),
                borrowEntry.getBorrowStart(),
                borrowEntry.getBorrowEnd(),
                borrowEntry.getBorrowStatus());
    }

    public List<BorrowEntryDto> mapToBorrowEntryDtoList(final List<BorrowEntry> borrowEntryList) {
        return borrowEntryList.stream()
                .map(b -> new BorrowEntryDto(
                        b.getId(),
                        b.getLibraryUser(),
                        b.getBookCopy(),
                        b.getBorrowStart(),
                        b.getBorrowEnd(),
                        b.getBorrowStatus()))
                .collect(Collectors.toList());
    }

    public List<BorrowEntry> mapToBorrowEntryList(final List<BorrowEntryDto> borrowEntries) {
        return borrowEntries.stream()
                .map(b -> new BorrowEntry(
                        b.getId(),
                        b.getLibraryUser(),
                        b.getBookCopy(),
                        b.getBorrowStart(),
                        b.getBorrowEnd(),
                        b.getBorrowStatus()))
                .collect(Collectors.toList());
    }

}
