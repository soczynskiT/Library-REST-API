package com.kodilla.library.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}
