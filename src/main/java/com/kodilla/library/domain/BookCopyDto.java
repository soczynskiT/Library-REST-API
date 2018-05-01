package com.kodilla.library.domain;

import com.kodilla.library.enums.BookCopyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyDto {
    private Long id;
    private Book book;
    private BookCopyStatus status;
    private List<BorrowEntry> borrowEntries;
}
