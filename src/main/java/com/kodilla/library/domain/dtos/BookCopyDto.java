package com.kodilla.library.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kodilla.library.domain.Book;
import com.kodilla.library.enums.BookCopyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookCopyDto {
    private Long id;
    private Book book;
    private BookCopyStatus status;

    @JsonIgnore
    private List<BorrowEntryDto> borrowEntries;
}
