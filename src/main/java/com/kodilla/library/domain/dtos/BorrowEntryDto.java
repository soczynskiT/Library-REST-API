package com.kodilla.library.domain.dtos;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowEntryDto {
    private Long id;
    private LibraryUser libraryUser;
    private BookCopy bookCopy;
    private Date borrowStart;
    private LocalDate borrowEnd;
    private BorrowStatus borrowStatus;
}
