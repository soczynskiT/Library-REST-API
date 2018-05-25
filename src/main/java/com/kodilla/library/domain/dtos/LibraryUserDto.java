package com.kodilla.library.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LibraryUserDto {
    private Long id;
    private String name;
    private String surname;
    private Date joinedDate;

    @JsonIgnore
    private List<BorrowEntryDto> borrowEntries;
}
