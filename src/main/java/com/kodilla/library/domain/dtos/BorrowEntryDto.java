package com.kodilla.library.domain.dtos;

import com.kodilla.library.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowEntryDto {
    private Long id;
    private LibraryUserDto libraryUserDto;
    private BookCopyDto bookCopyDto;
    private Date borrowStart;
    private Date borrowEnd;
    private BorrowStatus borrowStatus;
}
