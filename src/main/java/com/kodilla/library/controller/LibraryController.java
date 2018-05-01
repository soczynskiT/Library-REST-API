package com.kodilla.library.controller;


import com.kodilla.library.domain.*;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.mapper.LibraryUserMapper;
import com.kodilla.library.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class LibraryController {
    @Autowired
    DbService dbService;

    @Autowired
    LibraryUserMapper libraryUserMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookCopyMapper bookCopyMapper;


    @GetMapping(value = "/users")
    public List<LibraryUserDto> getLibraryUsers() {
        final List<LibraryUser> libraryUsers = dbService.getAllLibraryUsers();
        return libraryUserMapper.mapToLibraryUserDtoList(libraryUsers);
    }

    @GetMapping(value = "/users/{id}")
    public LibraryUserDto getTask(@PathVariable("id") final Long id) throws LibraryUserNotFoundException {
        final LibraryUser libraryUser = dbService.getLibraryUser(id).orElseThrow(LibraryUserNotFoundException::new);
        return libraryUserMapper.mapToLibraryUserDto(libraryUser);
    }

    @PostMapping(value = "/users", consumes = APPLICATION_JSON_VALUE)
    public void createLibraryUser(@RequestBody final LibraryUserDto libraryUserDto){
        final LibraryUser libraryUser = libraryUserMapper.mapToLibraryUser(libraryUserDto);
        dbService.saveLibraryUser(libraryUser);
    }

    @GetMapping(value = "/books")
    public List<Book> getBooks() {
        final List<Book> bookList = dbService.getAllBooks();
        return bookList;
    }

    @PostMapping(value = "/books", consumes = APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody final BookDto bookDto) {
        final Book book = bookMapper.mapToBook(bookDto);
        dbService.saveBook(book);
    }

    @GetMapping(value = "/copies")
    public List<BookCopyDto> getBookCopies() {
        final List<BookCopy> bookCopyList = dbService.getAllBookCopies();
        return bookCopyMapper.mapToBookCopyDtoList(bookCopyList);
    }

    @PostMapping(value = "/copies", consumes = APPLICATION_JSON_VALUE)
    public void createBookCopy(@RequestBody final BookCopyDto bookCopyDto ) {
        final BookCopy bookCopy = bookCopyMapper.mapToBookCopy(bookCopyDto);
        dbService.saveBookCopy(bookCopy);

    }

    @PutMapping(value = "/copies")
    public BookCopyDto updateBookCopyStatus(@RequestBody final BookCopyDto bookCopyDto) {
        final BookCopy updatedBookCopy = dbService.saveBookCopy(bookCopyMapper.mapToBookCopy(bookCopyDto));
        return bookCopyMapper.mapToBookCopyDto(updatedBookCopy);
    }

    @GetMapping(value = "/copies/available/{id}")
    public List<BookCopyDto> getListOfAvailableBookCopies(@PathVariable("id") final Long id) {
        final List<BookCopy> bookCopyList = dbService.getAllAvailableCopiesWithBookId(id);
        return bookCopyMapper.mapToBookCopyDtoList(bookCopyList);
    }

    public void borrowBookCopy() {
    }

    public void returnBookCopy() {
    }

}
