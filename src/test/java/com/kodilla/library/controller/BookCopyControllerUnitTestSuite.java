package com.kodilla.library.controller;

import com.google.gson.Gson;
import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.dtos.BookCopyDto;
import com.kodilla.library.enums.BookCopyStatus;
import com.kodilla.library.exceptions.BookNotFoundException;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.service.BookCopyService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookCopyController.class)
@RunWith(SpringRunner.class)
public class BookCopyControllerUnitTestSuite {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private BookCopyMapper bookCopyMapper;
    @MockBean
    private BookCopyService bookCopyService;

    @Test
    public void forGetBookCopiesShouldReturnAllBookCopiesByExistingBookId() throws Exception {
        //Given
        final Long existingBookId = 1L;
        final BookCopy bookCopy = new BookCopy();
        final BookCopy bookCopy1 = new BookCopy();
        final List<BookCopy> bookCopies = new ArrayList<>(Arrays.asList(bookCopy, bookCopy1));

        final BookCopyDto bookCopyDto = new BookCopyDto();
        final BookCopyDto bookCopyDto1 = new BookCopyDto();
        final List<BookCopyDto> bookCopyDtoList = new ArrayList<>(Arrays.asList(bookCopyDto, bookCopyDto1));

        when(bookCopyService.getAllCopiesByBookId(existingBookId)).thenReturn(bookCopies);
        when(bookCopyMapper.mapToBookCopyDtoList(bookCopies)).thenReturn(bookCopyDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/books/1/copies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void forGetBookCopiesShouldReturnEmptyCopiesListForExistingBookId() throws Exception {
        //Given
        final Long existingBookId = 1L;
        final List<BookCopy> bookCopies = new ArrayList<>();
        final List<BookCopyDto> bookCopyDtoList = new ArrayList<>();

        when(bookCopyService.getAllCopiesByBookId(existingBookId)).thenReturn(bookCopies);
        when(bookCopyMapper.mapToBookCopyDtoList(bookCopies)).thenReturn(bookCopyDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/books/1/copies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void forGetBookCopiesShouldThrowBookNotFoundExceptionForNotExistingBook() throws Exception {
        //Given
        final Long notExistingBookId = -1L;
        when(bookCopyService.getAllCopiesByBookId(notExistingBookId)).thenThrow(BookNotFoundException.class);

        //When & Then
        MvcResult mvcResult = mockMvc.perform(get("/v1/library/books/-1/copies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andReturn();

        assertThat(mvcResult.getResolvedException(), instanceOf(BookNotFoundException.class));
        assertThat(mvcResult.getResponse().getErrorMessage(), is("This book is not found in the system"));
    }

    @Test
    public void forCreateBookCopyShouldCreateAndReturnNewCopy() throws Exception {
        //Given
        final Long bookId = 1L;
        final BookCopyDto bookCopyDto = new BookCopyDto(1L, BookCopyStatus.LOST);
        final BookCopy createdBookCopy = new BookCopy(1L, new Book(), BookCopyStatus.LOST, new ArrayList<>());

        when(bookCopyMapper.mapToBookCopy(Matchers.any(BookCopyDto.class))).thenReturn(createdBookCopy);
        when(bookCopyService.saveBookCopy(createdBookCopy, bookId)).thenReturn(createdBookCopy);
        when(bookCopyMapper.mapToBookCopyDto(createdBookCopy)).thenReturn(bookCopyDto);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(bookCopyDto);

        //When & Then
        mockMvc.perform(post("/v1/library/books/1/copies").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("LOST")));
    }

    @Test
    public void forCreateBookCopyShouldThrowBookNotFoundExceptionFor() throws Exception {
        //Given
        final Long notExistingBookId = -1L;
        final BookCopyDto bookCopyDto = new BookCopyDto();
        final BookCopy createdBookCopy = new BookCopy();

        when(bookCopyMapper.mapToBookCopy(Matchers.any(BookCopyDto.class))).thenReturn(createdBookCopy);
        when(bookCopyService.saveBookCopy(createdBookCopy, notExistingBookId)).thenThrow(BookNotFoundException.class);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(bookCopyDto);

        //When & Then
        MvcResult mvcResult = mockMvc.perform(post("/v1/library/books/-1/copies").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(404))
                .andReturn();

        assertThat(mvcResult.getResolvedException(), instanceOf(BookNotFoundException.class));
        assertThat(mvcResult.getResponse().getErrorMessage(), is("This book is not found in the system"));
    }

    @Test
    public void forGetListOfBookCopiesWithStatusShouldReturnSpecifiedList() throws Exception {
        //Given
        final BookCopy bookCopy = new BookCopy(1L, new Book(), BookCopyStatus.LOST, new ArrayList<>());
        final BookCopy bookCopy2 = new BookCopy(2L, new Book(), BookCopyStatus.LOST, new ArrayList<>());
        final List<BookCopy> bookCopies = new ArrayList<>(Arrays.asList(bookCopy, bookCopy2));

        final BookCopyDto bookCopyDto = new BookCopyDto(1L, BookCopyStatus.LOST);
        final BookCopyDto bookCopyDto2 = new BookCopyDto(2L, BookCopyStatus.LOST);
        final List<BookCopyDto> bookCopyDtoList = new ArrayList<>(Arrays.asList(bookCopyDto, bookCopyDto2));

        when(bookCopyService.getAllCopiesWithBookIdAndStatus(anyLong(), Matchers.any(BookCopyStatus.class)))
                .thenReturn(bookCopies);
        when(bookCopyMapper.mapToBookCopyDtoList(bookCopies)).thenReturn(bookCopyDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/books/1/copies/LOST").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void forGetListOfBookCopiesWithStatusShouldReturnEmptyList() throws Exception {
        //Given
        final List<BookCopy> bookCopies = new ArrayList<>();
        final List<BookCopyDto> bookCopyDtoList = new ArrayList<>();

        when(bookCopyService.getAllCopiesWithBookIdAndStatus(anyLong(), Matchers.any(BookCopyStatus.class)))
                .thenReturn(bookCopies);
        when(bookCopyMapper.mapToBookCopyDtoList(bookCopies)).thenReturn(bookCopyDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/books/1/copies/LOST").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void forGetListOfBookCopiesWithStatusShouldThrowBookNotFound() throws Exception {
        when(bookCopyService.getAllCopiesWithBookIdAndStatus(anyLong(), Matchers.any(BookCopyStatus.class)))
                .thenThrow(BookNotFoundException.class);

        //When & Then
        MvcResult mvcResult = mockMvc.perform(get("/v1/library/books/1/copies/LOST").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andReturn();

        assertThat(mvcResult.getResolvedException(), instanceOf(BookNotFoundException.class));
        assertThat(mvcResult.getResponse().getErrorMessage(), is("This book is not found in the system"));
    }
}