package com.kodilla.library.mapper;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.dtos.BookCopyDto;
import com.kodilla.library.enums.BookCopyStatus;
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
public class BookCopyMapperUnitTestSuite {
    @Autowired
    private BookCopyMapper bookCopyMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookCopyMapperUnitTestSuite.class);

    @Test
    public void shouldMapToBookCopy() {
        //Given
        final BookCopyDto bookCopyDto = new BookCopyDto(1L, BookCopyStatus.LOST);

        //When
        final BookCopy mappedBookCopy = bookCopyMapper.mapToBookCopy(bookCopyDto);

        //Then
        assertEquals(1L, mappedBookCopy.getId().intValue());
        assertEquals(BookCopyStatus.LOST, mappedBookCopy.getStatus());
    }

    @Test
    public void shouldMapToBookCopyDto() {
        //Given
        final BookCopy bookCopy = new BookCopy(1L, new Book(), BookCopyStatus.LOST, new ArrayList<>());

        //When
        final BookCopyDto mappedBookCopy = bookCopyMapper.mapToBookCopyDto(bookCopy);

        //Then
        assertEquals(1L, mappedBookCopy.getId().intValue());
        assertEquals(BookCopyStatus.LOST, mappedBookCopy.getStatus());
    }

    @Test
    public void shouldMapToBookCopyDtoList() {
        //Given
        final BookCopy bookCopy = new BookCopy(1L, new Book(), BookCopyStatus.LOST, new ArrayList<>());
        final BookCopy bookCopy2 = new BookCopy(2L, new Book(), BookCopyStatus.AVAILABLE, new ArrayList<>());
        final List<BookCopy> bookCopies = new ArrayList<>(Arrays.asList(bookCopy, bookCopy2));

        //When
        final List<BookCopyDto> mappedBookCopies = bookCopyMapper.mapToBookCopyDtoList(bookCopies);

        //Then
        assertEquals(2, mappedBookCopies.size());

        LOGGER.info("Checking for mapped list content...");
        for (BookCopyDto bookCopyDto : mappedBookCopies) {
            System.out.println(
                    "ID: " + bookCopyDto.getId() +
                            "\nStatus: " + bookCopyDto.getStatus().toString()
            );
        }


    }
}