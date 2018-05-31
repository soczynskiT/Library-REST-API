package com.kodilla.library.mapper;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.dtos.BookCopyDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookCopyMapper {
    public BookCopy mapToBookCopy(final BookCopyDto bookCopyDto) {
        return new BookCopy(
                bookCopyDto.getId(),
                new Book(),
                bookCopyDto.getStatus(),
                new ArrayList<>());
    }

    public BookCopyDto mapToBookCopyDto(final BookCopy bookCopy) {
        return new BookCopyDto(
                bookCopy.getId(),
                bookCopy.getStatus());
    }

    public List<BookCopyDto> mapToBookCopyDtoList(final List<BookCopy> bookCopies) {
        return bookCopies.stream()
                .map(b -> new BookCopyDto(b.getId(), b.getStatus()))
                .collect(Collectors.toList());
    }
}
