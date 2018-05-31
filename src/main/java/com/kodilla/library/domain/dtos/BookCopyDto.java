package com.kodilla.library.domain.dtos;

import com.kodilla.library.enums.BookCopyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookCopyDto {
    private Long id;
    private BookCopyStatus status;
}
