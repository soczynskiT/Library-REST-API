package com.kodilla.library.controller;

import com.google.gson.Gson;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BorrowEntry;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dtos.BookCopyDto;
import com.kodilla.library.domain.dtos.BorrowEntryDto;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import com.kodilla.library.enums.BorrowStatus;
import com.kodilla.library.exceptions.BorrowEntryNotFoundException;
import com.kodilla.library.exceptions.LibraryUserNotFoundException;
import com.kodilla.library.exceptions.NoAvailableCopiesFoundedException;
import com.kodilla.library.mapper.BorrowEntryMapper;
import com.kodilla.library.mapper.LibraryUserMapper;
import com.kodilla.library.service.BorrowEntryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowEntryController.class)
@RunWith(SpringRunner.class)
public class BorrowEntryControllerUnitTestSuite {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BorrowEntryMapper borrowEntryMapper;
    @MockBean
    BorrowEntryService borrowEntryService;
    @MockBean
    LibraryUserMapper libraryUserMapper;

    @Test
    public void forBorrowBookShouldCreateNewBorrowEntry() throws Exception {
        //Given
        final Long existingBookId = 1L;
        final LibraryUser libraryUser = new LibraryUser();
        final LibraryUserDto libraryUserDto = new LibraryUserDto();
        final BorrowEntry borrowEntry = new BorrowEntry(1L, new LibraryUser(), new BookCopy(),
                null, null, BorrowStatus.IN_PROGRESS);
        final BorrowEntryDto borrowEntryDto = new BorrowEntryDto(1L, new LibraryUserDto(), new BookCopyDto(),
                null, null, BorrowStatus.IN_PROGRESS);

        when(libraryUserMapper.mapToLibraryUser(eq(libraryUserDto))).thenReturn(libraryUser);
        when(borrowEntryService.createBorrowEntry(eq(libraryUser), eq(existingBookId))).thenReturn(borrowEntry);
        when(borrowEntryMapper.mapToBorrowEntryDto(eq(borrowEntry))).thenReturn(borrowEntryDto);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(libraryUserDto);

        //When & Then
        mockMvc.perform(post("/v1/library/books/1/borrow").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.borrowStatus", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.libraryUserDto", notNullValue()))
                .andExpect(jsonPath("$.bookCopyDto", notNullValue()));
    }

    @Test
    public void forBorrowBookShouldReturnLibraryUserNotFoundException() throws Exception {
        //Given
        final Long existingBookId = 1L;
        final LibraryUser notExistingUser = new LibraryUser();
        final LibraryUserDto notExistingUserDto = new LibraryUserDto();

        when(libraryUserMapper.mapToLibraryUser(Matchers.any(LibraryUserDto.class))).thenReturn(notExistingUser);
        when(borrowEntryService.createBorrowEntry(eq(notExistingUser), eq(existingBookId)))
                .thenThrow(LibraryUserNotFoundException.class);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(notExistingUserDto);

        //When & Then
        MvcResult mvcResult = mockMvc.perform(post("/v1/library/books/1/borrow").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(404))
                .andReturn();

        assertThat(mvcResult.getResolvedException(), instanceOf(LibraryUserNotFoundException.class));
        assertThat(mvcResult.getResponse().getErrorMessage(), is("This user is not found in the system"));
    }

    @Test
    public void forBorrowBookShouldReturnNoAvailableCopiesFoundException() throws Exception {
        //Given
        final Long existingBookId = 1L;
        final LibraryUser libraryUser = new LibraryUser();
        final LibraryUserDto libraryUserDto = new LibraryUserDto();

        when(libraryUserMapper.mapToLibraryUser(eq(libraryUserDto))).thenReturn(libraryUser);
        when(borrowEntryService.createBorrowEntry(eq(libraryUser), eq(existingBookId)))
                .thenThrow(NoAvailableCopiesFoundedException.class);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(libraryUserDto);

        //When & Then
        MvcResult mvcResult = mockMvc.perform(post("/v1/library/books/1/borrow").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(404))
                .andReturn();

        assertThat(mvcResult.getResolvedException(), instanceOf(NoAvailableCopiesFoundedException.class));
        assertThat(mvcResult.getResponse().getErrorMessage(), is("There are no available, requested books to borrow"));
    }

    @Test
    public void forReturnBookShouldUpdateBorrowEntryStatus() throws Exception {
        //Given
        final BorrowEntryDto borrowEntryDto = new BorrowEntryDto(1L, new LibraryUserDto(), new BookCopyDto(),
                null, null, BorrowStatus.IN_PROGRESS);
        final BorrowEntry borrowEntry = new BorrowEntry(1L, new LibraryUser(), new BookCopy(),
                null, null, BorrowStatus.IN_PROGRESS);
        final BorrowEntry updatedEntry = new BorrowEntry(1L, new LibraryUser(), new BookCopy(),
                null, null, BorrowStatus.FINISHED);
        final BorrowEntryDto updatedEntryDto = new BorrowEntryDto(1L, new LibraryUserDto(), new BookCopyDto(),
                null, null, BorrowStatus.FINISHED);

        when(borrowEntryMapper.mapToBorrowEntry(eq(borrowEntryDto))).thenReturn(borrowEntry);
        when(borrowEntryService.returnBook(eq(borrowEntry))).thenReturn(updatedEntry);
        when(borrowEntryMapper.mapToBorrowEntryDto(eq(updatedEntry))).thenReturn(updatedEntryDto);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(borrowEntryDto);

        //When & Then
        mockMvc.perform(put("/v1/library/books/return").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.borrowStatus", is("FINISHED")))
                .andExpect(jsonPath("$.libraryUserDto", notNullValue()))
                .andExpect(jsonPath("$.bookCopyDto", notNullValue()));
    }

    @Test
    public void forReturnBookShouldThrowBorrowEntryNotFoundException() throws Exception {
        //Given
        final BorrowEntryDto notExistingEntryDto = new BorrowEntryDto(1L, new LibraryUserDto(), new BookCopyDto(),
                null, null, BorrowStatus.IN_PROGRESS);
        final BorrowEntry notExistingEntry = new BorrowEntry(1L, new LibraryUser(), new BookCopy(),
                null, null, BorrowStatus.IN_PROGRESS);

        when(borrowEntryMapper.mapToBorrowEntry(eq(notExistingEntryDto))).thenReturn(notExistingEntry);
        when(borrowEntryService.returnBook(eq(notExistingEntry))).thenThrow(BorrowEntryNotFoundException.class);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(notExistingEntryDto);

        //When & Then
        MvcResult mvcResult = mockMvc.perform(put("/v1/library/books/return").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(404))
                .andReturn();

        assertThat(mvcResult.getResolvedException(), instanceOf(BorrowEntryNotFoundException.class));
        assertThat(mvcResult.getResponse().getErrorMessage(), is("No such a borrow entry in system"));
    }
}