package com.kodilla.library.mapper;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.dtos.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookMapperUnitTestSuite {
    @Autowired
    private BookMapper bookMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookCopyMapperUnitTestSuite.class);

    @Test
    public void shouldMapToBook() {
        //Given
        final BookDto bookDto = new BookDto(1L, "testTitle", "testAuthor", 2000);

        //When
        final Book mappedBook = bookMapper.mapToBook(bookDto);

        //Then
        assertEquals(1L, mappedBook.getId().intValue());
        assertEquals("testTitle", mappedBook.getTitle());
        assertEquals("testAuthor", mappedBook.getAuthor());
        assertEquals(2000, mappedBook.getPublicationYear());
    }

    @Test
    public void shouldMapToBookDto() {
        //Given
        final Book book = new Book(1L, "testTitle", "testAuthor", 2000, new ArrayList<>());

        //When
        final BookDto mappedBook = bookMapper.mapToBookDto(book);

        //Then
        assertEquals(1L, mappedBook.getId().intValue());
        assertEquals("testTitle", mappedBook.getTitle());
        assertEquals("testAuthor", mappedBook.getAuthor());
        assertEquals(2000, mappedBook.getPublicationYear());
    }

    @Test
    public void shouldMapToBookDtoList() {
        //Given
        final Book book = new Book(1L, "testTitle", "testAuthor", 2000, new ArrayList<>());
        final Book book2 = new Book(2L, "testTitle2", "testAuthor2", 2001, new ArrayList<>());
        final List<Book> books = new ArrayList<>(Arrays.asList(book, book2));

        //When
        final List<BookDto> mappedList = bookMapper.mapToBookDtoList(books);

        //Then
        assertEquals(2, mappedList.size());

        LOGGER.info("Checking content of mapped list...");
        for (BookDto bookDto : mappedList) {
            System.out.println(
                    "ID: " + bookDto.getId() +
                            "\nTitle: " + bookDto.getTitle() +
                            "\nAuthor: " + bookDto.getAuthor() +
                            "\nPubYear: " + bookDto.getPublicationYear()
            );
        }
    }
}