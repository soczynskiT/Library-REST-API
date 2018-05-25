package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.dtos.BookCopyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookCopyMapper {

    @Autowired
    BorrowEntryMapper borrowEntryMapper;

    public BookCopy mapToBookCopy(final BookCopyDto bookCopyDto) {
        return new BookCopy(
                bookCopyDto.getId(),
                bookCopyDto.getBook(),
                bookCopyDto.getStatus(),
                borrowEntryMapper.mapToBorrowEntryList(bookCopyDto.getBorrowEntries()));
    }

    public BookCopyDto mapToBookCopyDto(final BookCopy bookCopy) {
        return new BookCopyDto(
                bookCopy.getId(),
                bookCopy.getBook(),
                bookCopy.getStatus(),
                borrowEntryMapper.mapToBorrowEntryDtoList(bookCopy.getBorrowEntries()));
    }

    public List<BookCopyDto> mapToBookCopyDtoList(final List<BookCopy> bookCopies) {
        return bookCopies.stream()
                .map(b -> new BookCopyDto(b.getId(), b.getBook(), b.getStatus(), borrowEntryMapper.mapToBorrowEntryDtoList(b.getBorrowEntries())))
                .collect(Collectors.toList());
    }

    public List<BookCopy> mapToBookCopyList(final List<BookCopyDto> bookCopyDtos) {
        return bookCopyDtos.stream()
                .map(b -> new BookCopy(b.getId(), b.getBook(), b.getStatus(), borrowEntryMapper.mapToBorrowEntryList(b.getBorrowEntries())))
                .collect(Collectors.toList());
    }
}
