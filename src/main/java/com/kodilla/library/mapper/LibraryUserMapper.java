package com.kodilla.library.mapper;

import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryUserMapper {
    public LibraryUser mapToLibraryUser(final LibraryUserDto libraryUserDto) {
        return new LibraryUser(
                libraryUserDto.getId(),
                libraryUserDto.getName(),
                libraryUserDto.getSurname(),
                libraryUserDto.getJoinedDate(),
                new ArrayList<>());
    }

    public LibraryUserDto mapToLibraryUserDto(final LibraryUser libraryUser) {
        return new LibraryUserDto(
                libraryUser.getId(),
                libraryUser.getName(),
                libraryUser.getSurname(),
                libraryUser.getJoinedDate());
    }

    public List<LibraryUserDto> mapToLibraryUserDtoList(final List<LibraryUser> libraryUsers) {
        return libraryUsers.stream()
                .map(l -> new LibraryUserDto(l.getId(), l.getName(), l.getSurname(), l.getJoinedDate()))
                .collect(Collectors.toList());
    }
}
