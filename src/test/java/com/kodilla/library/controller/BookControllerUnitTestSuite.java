package com.kodilla.library.controller;

import com.google.gson.Gson;
import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.dtos.BookDto;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@RunWith(SpringRunner.class)
public class BookControllerUnitTestSuite {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private BookMapper bookMapper;
    @MockBean
    private BookService bookService;

    @Test
    public void forGetBooksShouldReturnAllBooksList() throws Exception {
        //Given
        final Book book = new Book();
        final Book book1 = new Book();
        final List<Book> books = new ArrayList<>(Arrays.asList(book, book1));

        final BookDto bookDto = new BookDto();
        final BookDto bookDto1 = new BookDto();
        final List<BookDto> bookDtoList = new ArrayList<>(Arrays.asList(bookDto, bookDto1));

        when(bookService.getAllBooks()).thenReturn(books);
        when(bookMapper.mapToBookDtoList(eq(books))).thenReturn(bookDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void forGetBooksShouldReturnEmptyList() throws Exception {
        //Given
        final List<Book> books = new ArrayList<>();
        final List<BookDto> bookDtoList = new ArrayList<>();

        when(bookService.getAllBooks()).thenReturn(books);
        when(bookMapper.mapToBookDtoList(eq(books))).thenReturn(bookDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void forCreateBookShouldCreateAndReturnNewBook() throws Exception {
        //Given
        final BookDto bookDto = new BookDto(1L, "title", "name", 1999);
        final Book createdBook = new Book(1L, "title", "name", 1999, new ArrayList<>());
        final Book savedBook = new Book(1L, "title", "name", 1999, new ArrayList<>());
        final BookDto savedBookDto = new BookDto(1L, "title", "name", 1999);

        when(bookMapper.mapToBook(eq(bookDto))).thenReturn(createdBook);
        when(bookService.saveBook(eq(createdBook))).thenReturn(savedBook);
        when(bookMapper.mapToBookDto(eq(savedBook))).thenReturn(savedBookDto);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(bookDto);

        //When & Then
        mockMvc.perform(post("/v1/library/books").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.author", is("name")))
                .andExpect(jsonPath("$.publicationYear", is(1999)))
                .andExpect(status().isOk());
    }

    @Test
    public void forUpdateBookShouldUpdateAndReturnUpdatedBook() throws Exception {
        //Given
        final BookDto bookDto = new BookDto(1L, "title", "name", 1999);
        final Book createdBook = new Book(1L, "title", "name", 1999, new ArrayList<>());
        final Book updatedBook = new Book(1L, "title", "name", 1999, new ArrayList<>());
        final BookDto updatedBookDto = new BookDto(1L, "title", "name", 1999);

        when(bookMapper.mapToBook(eq(bookDto))).thenReturn(createdBook);
        when(bookService.saveBook(eq(createdBook))).thenReturn(updatedBook);
        when(bookMapper.mapToBookDto(eq(updatedBook))).thenReturn(updatedBookDto);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(bookDto);
        System.out.println(jsonContent);

        //When & Then
        mockMvc.perform(put("/v1/library/books").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.author", is("name")))
                .andExpect(jsonPath("$.publicationYear", is(1999)))
                .andExpect(status().isOk());
    }
}