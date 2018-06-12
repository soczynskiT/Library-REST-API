package com.kodilla.library.domain.dtos;

import com.kodilla.library.enums.BookCopyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyDto {
    private Long id;
    private BookCopyStatus status;
}
