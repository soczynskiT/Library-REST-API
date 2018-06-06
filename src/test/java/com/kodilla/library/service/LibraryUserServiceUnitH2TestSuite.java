package com.kodilla.library.service;

import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.exceptions.LibraryUserNotFoundException;
import com.kodilla.library.repository.LibraryUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryUserServiceUnitH2TestSuite {
    @Autowired
    LibraryUserService libraryUserService;
    @Autowired
    LibraryUserRepository libraryUserRepository;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    @Test
    public void shouldSaveLibraryUserToDb() throws ParseException {
        //Given
        final LibraryUser libraryUser = new LibraryUser("testName", "testSurname",
                DATE_FORMAT.parse("2000/01/01"));

        //When
        libraryUserService.saveLibraryUser(libraryUser);
        final Long userId = libraryUser.getId();
        final Optional<LibraryUser> savedUser = libraryUserRepository.findById(userId);

        //Then
        assertTrue(savedUser.isPresent());
        assertEquals(userId, savedUser.get().getId());
        assertEquals("testName", savedUser.get().getName());
        assertEquals("testSurname", savedUser.get().getSurname());
        assertEquals(DATE_FORMAT.parse("2000/01/01"), savedUser.get().getJoinedDate());
    }

    @Test
    public void shouldReturnExistingLibraryUser() throws ParseException {
        //Given
        final LibraryUser libraryUser = new LibraryUser("testName", "testSurname",
                DATE_FORMAT.parse("2000/01/01"));
        libraryUserRepository.save(libraryUser);
        final Long savedUserId = libraryUser.getId();

        //When
        final LibraryUser foundedUser = libraryUserService.getLibraryUser(savedUserId);

        //Then
        assertEquals(savedUserId, foundedUser.getId());
        assertEquals("testName", foundedUser.getName());
        assertEquals("testSurname", foundedUser.getSurname());
        assertEquals(DATE_FORMAT.parse("2000/01/01"), foundedUser.getJoinedDate());
    }

    @Test
    public void shouldThrowExceptionForGettingNotExistingUser() {
        //Given
        final Long notExistingUserId = (long) -1;

        //When & Then
        assertThatThrownBy(() -> libraryUserService.getLibraryUser(notExistingUserId))
                .isInstanceOf(LibraryUserNotFoundException.class);
    }

    @Test
    public void getAllLibraryUsers() throws ParseException {
        //Given
        final LibraryUser libraryUser = new LibraryUser("testName", "testSurname",
                DATE_FORMAT.parse("2000/01/01"));
        final LibraryUser libraryUser2 = new LibraryUser("testName2", "testSurname2",
                DATE_FORMAT.parse("2002/02/02"));
        libraryUserRepository.save(libraryUser);
        libraryUserRepository.save(libraryUser2);

        //When
        final List<LibraryUser> libraryUsers = libraryUserService.getAllLibraryUsers();

        //Then
        assertEquals(2, libraryUsers.size());
    }

    @Test
    public void shouldReturnEmptyListForEmptyUsersDb() {
        //Given & When
        final List<LibraryUser> libraryUsers = libraryUserService.getAllLibraryUsers();

        //Then
        assertEquals(0, libraryUsers.size());
    }
}