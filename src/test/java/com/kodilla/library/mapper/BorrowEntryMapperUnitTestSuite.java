package com.kodilla.library.mapper;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BorrowEntry;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dtos.BookCopyDto;
import com.kodilla.library.domain.dtos.BorrowEntryDto;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.enums.BorrowStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowEntryMapperUnitTestSuite {
    @Autowired
    private BorrowEntryMapper borrowEntryMapper;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    @Test
    public void shouldMapToBorrowEntry() throws ParseException {
        //Given
        final LibraryUserDto libraryUserDto = new LibraryUserDto(1L, "testName", "testSurname",
                DATE_FORMAT.parse("2000/01/01"));
        final BookCopyDto bookCopyDto = new BookCopyDto(1L, BookCopyStatus.LOST);
        final BorrowEntryDto borrowEntryDto = new BorrowEntryDto(1L, libraryUserDto, bookCopyDto,
                DATE_FORMAT.parse("2000/01/01"), DATE_FORMAT.parse("2000/02/02"), BorrowStatus.FINISHED);

        //When
        final BorrowEntry mappedBorrowEntry = borrowEntryMapper.mapToBorrowEntry(borrowEntryDto);

        //Then
        assertEquals(1L, mappedBorrowEntry.getId().intValue());
        assertEquals(1L, mappedBorrowEntry.getLibraryUser().getId().intValue());
        assertEquals("testName", mappedBorrowEntry.getLibraryUser().getName());
        assertEquals("testSurname", mappedBorrowEntry.getLibraryUser().getSurname());
        assertEquals(DATE_FORMAT.parse("2000/01/01"), mappedBorrowEntry.getLibraryUser().getJoinedDate());
        assertEquals(1L, mappedBorrowEntry.getBookCopy().getId().intValue());
        assertEquals(BookCopyStatus.LOST, mappedBorrowEntry.getBookCopy().getStatus());
        assertEquals(DATE_FORMAT.parse("2000/01/01"), mappedBorrowEntry.getBorrowStart());
        assertEquals(DATE_FORMAT.parse("2000/02/02"), mappedBorrowEntry.getBorrowEnd());
        assertEquals(BorrowStatus.FINISHED, mappedBorrowEntry.getBorrowStatus());
    }

    @Test
    public void shouldMapToBorrowEntryDto() throws ParseException {
        //Given
        final LibraryUser libraryUser = new LibraryUser(1L, "testName", "testSurname",
                DATE_FORMAT.parse("2000/01/01"), new ArrayList<>());
        final BookCopy bookCopy = new BookCopy(1L, new Book(), BookCopyStatus.LOST, new ArrayList<>());
        final BorrowEntry borrowEntry = new BorrowEntry(1L, libraryUser, bookCopy,
                DATE_FORMAT.parse("2000/01/01"), DATE_FORMAT.parse("2000/02/02"), BorrowStatus.FINISHED);

        //When
        final BorrowEntryDto mappedBorrowEntry = borrowEntryMapper.mapToBorrowEntryDto(borrowEntry);

        //Then
        assertEquals(1L, mappedBorrowEntry.getId().intValue());
        assertEquals(1L, mappedBorrowEntry.getLibraryUserDto().getId().intValue());
        assertEquals("testName", mappedBorrowEntry.getLibraryUserDto().getName());
        assertEquals("testSurname", mappedBorrowEntry.getLibraryUserDto().getSurname());
        assertEquals(DATE_FORMAT.parse("2000/01/01"), mappedBorrowEntry.getLibraryUserDto().getJoinedDate());
        assertEquals(1L, mappedBorrowEntry.getBookCopyDto().getId().intValue());
        assertEquals(BookCopyStatus.LOST, mappedBorrowEntry.getBookCopyDto().getStatus());
        assertEquals(DATE_FORMAT.parse("2000/01/01"), mappedBorrowEntry.getBorrowStart());
        assertEquals(DATE_FORMAT.parse("2000/02/02"), mappedBorrowEntry.getBorrowEnd());
        assertEquals(BorrowStatus.FINISHED, mappedBorrowEntry.getBorrowStatus());
    }
}