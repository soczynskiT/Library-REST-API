package com.kodilla.library.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LibraryUserDto {
    private Long id;
    private String name;
    private String surname;
    private Date joinedDate;
}
