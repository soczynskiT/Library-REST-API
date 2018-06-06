package com.kodilla.library.controller;

import com.google.gson.Gson;
import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.domain.dtos.LibraryUserDto;
import com.kodilla.library.exceptions.LibraryUserNotFoundException;
import com.kodilla.library.mapper.LibraryUserMapper;
import com.kodilla.library.service.LibraryUserService;
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

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

@WebMvcTest(LibraryUserController.class)
@RunWith(SpringRunner.class)
public class LibraryUserControllerUnitTestSuite {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    LibraryUserMapper libraryUserMapper;
    @MockBean
    LibraryUserService libraryUserService;

    @Test
    public void forGetLibraryUsersShouldReturnAllUsers() throws Exception {
        //Given
        final LibraryUser libraryUser = new LibraryUser();
        final LibraryUser libraryUser1 = new LibraryUser();
        final List<LibraryUser> libraryUsers = new ArrayList<>(Arrays.asList(libraryUser, libraryUser1));

        final LibraryUserDto libraryUserDto = new LibraryUserDto();
        final LibraryUserDto libraryUser1Dto = new LibraryUserDto();
        final List<LibraryUserDto> libraryUsersDto = new ArrayList<>(Arrays.asList(libraryUserDto, libraryUser1Dto));

        when(libraryUserService.getAllLibraryUsers()).thenReturn(libraryUsers);
        when(libraryUserMapper.mapToLibraryUserDtoList(libraryUsers)).thenReturn(libraryUsersDto);

        //When & Then
        mockMvc.perform(get("/v1/library/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void forGetLibraryUsersShouldReturnEmptyList() throws Exception {
        //Given
        final List<LibraryUser> libraryUsers = new ArrayList<>();
        final List<LibraryUserDto> libraryUsersDto = new ArrayList<>();

        when(libraryUserService.getAllLibraryUsers()).thenReturn(libraryUsers);
        when(libraryUserMapper.mapToLibraryUserDtoList(libraryUsers)).thenReturn(libraryUsersDto);

        //When & Then
        mockMvc.perform(get("/v1/library/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void forGetUserWithIdShouldReturnUserWithRequestedId() throws Exception {
        //Given
        final LibraryUser libraryUser = new LibraryUser(1L, "name", "surname", new Date(), new ArrayList<>());
        final LibraryUserDto libraryUserDto = new LibraryUserDto(1L, "name", "surname", new Date());

        when(libraryUserService.getLibraryUser(1L)).thenReturn(libraryUser);
        when(libraryUserMapper.mapToLibraryUserDto(libraryUser)).thenReturn(libraryUserDto);

        //When & Then
        mockMvc.perform(get("/v1/library/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.surname", is("surname")));
    }

    @Test()
    public void forGetUserWithIdShouldThrowLibraryUserNotFoundException() throws Exception {
        //Given
        when(libraryUserService.getLibraryUser(anyLong())).thenThrow(LibraryUserNotFoundException.class);

        //When & Then
        MvcResult mvcResult = mockMvc.perform(get("/v1/library/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andReturn();

        assertThat(mvcResult.getResolvedException(), instanceOf(LibraryUserNotFoundException.class));
        assertThat(mvcResult.getResponse().getErrorMessage(), is("This user is not found in the system"));
    }

    @Test
    public void forCreateLibraryUserShouldCreateAndReturnNewUser() throws Exception {
        //Given
        final LibraryUserDto libraryUserDto = new LibraryUserDto(1L, "name", "surname", null);
        final LibraryUser createdLibraryUser = new LibraryUser(1L, "name", "surname", null, new ArrayList<>());
        final LibraryUser savedUser = new LibraryUser(1L, "name", "surname", null, new ArrayList<>());
        final LibraryUserDto savedUserDto = new LibraryUserDto(1L, "name", "surname", null);

        when(libraryUserMapper.mapToLibraryUser(Matchers.any(LibraryUserDto.class))).thenReturn(createdLibraryUser);
        when(libraryUserService.saveLibraryUser(createdLibraryUser)).thenReturn(savedUser);
        when(libraryUserMapper.mapToLibraryUserDto(savedUser)).thenReturn(savedUserDto);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(libraryUserDto);

        //When & Then
        mockMvc.perform(post("/v1/library/users").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.surname", is("surname")));
    }
}