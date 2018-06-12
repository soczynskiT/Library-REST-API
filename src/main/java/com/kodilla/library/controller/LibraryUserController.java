package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import com.kodilla.library.mapper.LibraryUserMapper;
import com.kodilla.library.service.LibraryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/users")
public class LibraryUserController {

    private final LibraryUserMapper libraryUserMapper;
    private final LibraryUserService libraryUserService;

    @Autowired
    public LibraryUserController(final LibraryUserMapper libraryUserMapper, final LibraryUserService libraryUserService) {
        this.libraryUserMapper = libraryUserMapper;
        this.libraryUserService = libraryUserService;
    }

    @GetMapping
    public List<LibraryUserDto> getLibraryUsers() {
        final List<LibraryUser> libraryUsers = libraryUserService.getAllLibraryUsers();
        return libraryUserMapper.mapToLibraryUserDtoList(libraryUsers);
    }

    @GetMapping(value = "/{id}")
    public LibraryUserDto getUserWithId(@PathVariable("id") final Long id) {
        final LibraryUser libraryUser = libraryUserService.getLibraryUser(id);
        return libraryUserMapper.mapToLibraryUserDto(libraryUser);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public LibraryUserDto createLibraryUser(@RequestBody final LibraryUserDto libraryUserDto) {
        final LibraryUser createdLibraryUser = libraryUserMapper.mapToLibraryUser(libraryUserDto);
        final LibraryUser savedUser = libraryUserService.saveLibraryUser(createdLibraryUser);
        return libraryUserMapper.mapToLibraryUserDto(savedUser);
    }
}
