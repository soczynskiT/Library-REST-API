package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryUserDto {
    private Long id;
    private String name;
    private String surname;
    private Date joinedDate;
    private List<BorrowEntry> borrowEntriesList;
}
