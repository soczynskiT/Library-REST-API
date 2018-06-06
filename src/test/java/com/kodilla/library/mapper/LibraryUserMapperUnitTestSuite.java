package com.kodilla.library.mapper;

import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryUserMapperUnitTestSuite {
    @Autowired
    private LibraryUserMapper libraryUserMapper;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryUserMapperUnitTestSuite.class);

    @Test
    public void shouldMapToLibraryUser() throws ParseException {
        //Given
        final LibraryUserDto libraryUserDto = new LibraryUserDto(1L, "testName", "testSurname",
                DATE_FORMAT.parse("2000/01/01"));

        //When
        final LibraryUser mappedUser = libraryUserMapper.mapToLibraryUser(libraryUserDto);

        //Then
        assertEquals(1L, mappedUser.getId().intValue());
        assertEquals("testName", mappedUser.getName());
        assertEquals("testSurname", mappedUser.getSurname());
        assertEquals(DATE_FORMAT.parse("2000/01/01"), mappedUser.getJoinedDate());
    }

    @Test
    public void shouldMapToLibraryUserDto() throws ParseException {
        //Given
        final LibraryUser libraryUser = new LibraryUser(1L, "testName", "testSurname",
                DATE_FORMAT.parse("2000/01/01"), new ArrayList<>());

        //When
        final LibraryUserDto mappedUserDto = libraryUserMapper.mapToLibraryUserDto(libraryUser);

        //Then
        assertEquals(1L, mappedUserDto.getId().intValue());
        assertEquals("testName", mappedUserDto.getName());
        assertEquals("testSurname", mappedUserDto.getSurname());
        assertEquals(DATE_FORMAT.parse("2000/01/01"), mappedUserDto.getJoinedDate());
    }

    @Test
    public void shouldMapToLibraryUserDtoList() throws ParseException {
        //Given
        final LibraryUser libraryUser = new LibraryUser(1L, "testName", "testSurname",
                DATE_FORMAT.parse("2000/01/01"), new ArrayList<>());
        final LibraryUser libraryUser2 = new LibraryUser(2L, "testName2", "testSurname2",
                DATE_FORMAT.parse("2000/02/02"), new ArrayList<>());
        final List<LibraryUser> libraryUsers = new ArrayList<>(Arrays.asList(libraryUser, libraryUser2));

        //When
        final List<LibraryUserDto> mappedLibraryUsersList = libraryUserMapper.mapToLibraryUserDtoList(libraryUsers);

        //Then
        assertEquals(2, mappedLibraryUsersList.size());

        LOGGER.info("Checking content of mapped list...");
        for (LibraryUserDto userDto : mappedLibraryUsersList) {
            System.out.println(
                    "User ID: " + userDto.getId() +
                            "\nName: " + userDto.getName() +
                            "\nSurname: " + userDto.getSurname() +
                            "\nJoined date: " + userDto.getJoinedDate().toString()
            );
        }
    }
}