package com.kodilla.library.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;

    @JsonIgnore
    private List<BookCopyDto> bookCopies;
}
