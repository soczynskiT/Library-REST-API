package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookCopyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookCopyMapper {
    public BookCopy mapToBookCopy(final BookCopyDto bookCopyDto) {
        return new BookCopy(
                bookCopyDto.getId(),
                bookCopyDto.getBook(),
                bookCopyDto.getStatus(),
                bookCopyDto.getBorrowEntries());
    }

    public BookCopyDto mapToBookCopyDto(final BookCopy bookCopy) {
        return new BookCopyDto(
                bookCopy.getId(),
                bookCopy.getBook(),
                bookCopy.getStatus(),
                bookCopy.getBorrowEntries());
    }

    public List<BookCopyDto> mapToBookCopyDtoList(final List<BookCopy> bookCopyList) {
        return bookCopyList.stream()
                .map(b -> new BookCopyDto(b.getId(), b.getBook(), b.getStatus(), b.getBorrowEntries()))
                .collect(Collectors.toList());
    }
}
