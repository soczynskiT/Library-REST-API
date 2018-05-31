package com.kodilla.library.controller;

import com.kodilla.library.domain.*;
import com.kodilla.library.domain.dtos.BookCopyDto;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.service.BookCopiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class BookCopyController {
    @Autowired
    private BookCopyMapper bookCopyMapper;
    @Autowired
    private BookCopiesService bookCopiesService;

    @GetMapping(value = "books/{bookId}/copies")
    public List<BookCopyDto> getBookCopies(@PathVariable("bookId") final Long id) {
        final List<BookCopy> bookCopies = bookCopiesService.getAllCopiesByBookId(id);
        return bookCopyMapper.mapToBookCopyDtoList(bookCopies);
    }

    @PostMapping(value = "books/{bookId}/copies", consumes = APPLICATION_JSON_VALUE)
    public BookCopyDto createBookCopy(@RequestBody final BookCopyDto bookCopyDto,
                                      @PathVariable("bookId") final Long bookId) {
        final BookCopy createdBookCopy = bookCopyMapper.mapToBookCopy(bookCopyDto);
        bookCopiesService.saveBookCopy(createdBookCopy, bookId);
        return bookCopyMapper.mapToBookCopyDto(createdBookCopy);
    }

    @GetMapping(value = "books/{id}/copies/{status}")
    public List<BookCopyDto> getListOfBookCopiesWithStatus(@PathVariable("id") final Long bookId,
                                                           @PathVariable("status") final BookCopyStatus bookCopyStatus) {
        final List<BookCopy> requestedCopies = bookCopiesService.getAllCopiesWithBookIdAndStatus(bookId, bookCopyStatus);
        return bookCopyMapper.mapToBookCopyDtoList(requestedCopies);
    }
}
