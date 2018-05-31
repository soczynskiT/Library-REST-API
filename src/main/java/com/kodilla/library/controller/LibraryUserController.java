package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import com.kodilla.library.mapper.LibraryUserMapper;
import com.kodilla.library.service.LibraryUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/users")
public class LibraryUserController {

    @Autowired
    private LibraryUserMapper libraryUserMapper;
    @Autowired
    private LibraryUsersService libraryUsersService;

    @GetMapping
    public List<LibraryUserDto> getLibraryUsers() {
        final List<LibraryUser> libraryUsers = libraryUsersService.getAllLibraryUsers();
        return libraryUserMapper.mapToLibraryUserDtoList(libraryUsers);
    }

    @GetMapping(value = "/{id}")
    public LibraryUserDto getUserWithId(@PathVariable("id") final Long id) {
        final LibraryUser libraryUser = libraryUsersService.getLibraryUser(id);
        return libraryUserMapper.mapToLibraryUserDto(libraryUser);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public LibraryUserDto createLibraryUser(@RequestBody final LibraryUserDto libraryUserDto) {
        final LibraryUser createdLibraryUser = libraryUserMapper.mapToLibraryUser(libraryUserDto);
        libraryUsersService.saveLibraryUser(createdLibraryUser);
        return libraryUserMapper.mapToLibraryUserDto(createdLibraryUser);
    }
}
