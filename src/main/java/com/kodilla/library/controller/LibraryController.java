package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dtos.BookCopyDto;
import com.kodilla.library.domain.dtos.BookDto;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.mapper.LibraryUserMapper;
import com.kodilla.library.service.BookCopiesService;
import com.kodilla.library.service.BooksService;
import com.kodilla.library.service.BorrowEntriesService;
import com.kodilla.library.service.LibraryUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class LibraryController {

    @Autowired
    LibraryUserMapper libraryUserMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookCopyMapper bookCopyMapper;
    @Autowired
    LibraryUsersService libraryUsersService;
    @Autowired
    BooksService booksService;
    @Autowired
    BookCopiesService bookCopiesService;
    @Autowired
    BorrowEntriesService borrowEntriesService;

    @GetMapping(value = "/users")
    public List<LibraryUserDto> getLibraryUsers() {
        final List<LibraryUser> libraryUsers = libraryUsersService.getAllLibraryUsers();
        return libraryUserMapper.mapToLibraryUserDtoList(libraryUsers);
    }

    @GetMapping(value = "/users/{id}")
    public LibraryUserDto getTask(@PathVariable("id") final Long id) throws LibraryUserNotFoundException {
        final LibraryUser libraryUser = libraryUsersService.getLibraryUser(id).orElseThrow(LibraryUserNotFoundException::new);
        return libraryUserMapper.mapToLibraryUserDto(libraryUser);
    }

    @PostMapping(value = "/users", consumes = APPLICATION_JSON_VALUE)
    public LibraryUserDto createLibraryUser(@RequestBody final LibraryUserDto libraryUserDto){
        final LibraryUser createdLibraryUser = libraryUserMapper.mapToLibraryUser(libraryUserDto);
        libraryUsersService.saveLibraryUser(createdLibraryUser);
        return libraryUserMapper.mapToLibraryUserDto(createdLibraryUser);
    }

    @GetMapping(value = "/books")
    public List<BookDto> getBooks() {
        final List<Book> books = booksService.getAllBooks();
        return bookMapper.mapToBookDtoList(books);
    }

    @PostMapping(value = "/books", consumes = APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody final BookDto bookDto) {
        final Book createdBook = bookMapper.mapToBook(bookDto);
        booksService.saveBook(createdBook);
        return bookMapper.mapToBookDto(createdBook);
    }

    @PutMapping(value = "/books")
    public BookDto putBook(@RequestBody final BookDto bookDto) {
        final Book createdBook = bookMapper.mapToBook(bookDto);
        booksService.saveBook(createdBook);
        return bookMapper.mapToBookDto(createdBook);
    }

    @GetMapping(value = "books/{id}/copies")
    public List<BookCopyDto> getBookCopies(@PathVariable("id") final Long id) throws LibraryUserNotFoundException {
        final List<BookCopy> bookCopies = bookCopiesService.getAllCopiesByBookId(id).orElseThrow(LibraryUserNotFoundException::new);
        return bookCopyMapper.mapToBookCopyDtoList(bookCopies);
    }

    @PostMapping(value = "books/copies", consumes = APPLICATION_JSON_VALUE)
    public BookCopyDto createBookCopy(@RequestBody final BookCopyDto bookCopyDto ) {
        final BookCopy createdBookCopy = bookCopyMapper.mapToBookCopy(bookCopyDto);
        bookCopiesService.saveBookCopy(createdBookCopy);
        return bookCopyMapper.mapToBookCopyDto(createdBookCopy);
    }

    @PutMapping(value = "books/copies")
    public BookCopyDto updateBookCopyStatus(@RequestBody final BookCopyDto bookCopyDto) {
        final BookCopy updatedBookCopy = bookCopiesService.saveBookCopy(bookCopyMapper.mapToBookCopy(bookCopyDto));
        return bookCopyMapper.mapToBookCopyDto(updatedBookCopy);
    }

    @GetMapping(value = "books/{id}/copies/{status}")
    public List<BookCopyDto> getListOfAvailableBookCopies(@PathVariable("id") final Long bookId,
                                                          @PathVariable("status") final BookCopyStatus bookCopyStatus) throws LibraryUserNotFoundException {
        final List<BookCopy> availableCopiesWithBookId = bookCopiesService.getAllAvailableCopiesWithBookId(bookId, bookCopyStatus).orElseThrow(LibraryUserNotFoundException::new);
        return bookCopyMapper.mapToBookCopyDtoList(availableCopiesWithBookId);
    }
/*
    public void borrowBookCopy() {  // z listy ksiazek book/{id}/borrow
    }

    public void returnBookCopy()
    }
*/
}
