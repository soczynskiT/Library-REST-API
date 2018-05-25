package com.kodilla.library.mapper;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.dtos.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    @Autowired
    BookCopyMapper bookCopyMapper;

    public Book mapToBook(final BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getPublicationYear(),
                bookCopyMapper.mapToBookCopyList(bookDto.getBookCopies()));
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                bookCopyMapper.mapToBookCopyDtoList(book.getBookCopies()));
    }

    public List<BookDto> mapToBookDtoList(final List<Book> bookList) {
        return bookList.stream()
                .map(b -> new BookDto(b.getId(), b.getTitle(), b.getAuthor(), b.getPublicationYear(), bookCopyMapper.mapToBookCopyDtoList(b.getBookCopies())))
                .collect(Collectors.toList());
    }
}
