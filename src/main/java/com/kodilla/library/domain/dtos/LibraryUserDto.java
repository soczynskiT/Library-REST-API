package com.kodilla.library.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryUserDto {
    private Long id;
    private String name;
    private String surname;
    private Date joinedDate;
}
