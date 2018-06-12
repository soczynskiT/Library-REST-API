package com.kodilla.library.service;

import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.exceptions.LibraryUserNotFoundException;
import com.kodilla.library.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryUserService {
    private final LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserService(final LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    public LibraryUser saveLibraryUser(final LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    public LibraryUser getLibraryUser(final Long id) throws LibraryUserNotFoundException {
        return libraryUserRepository.findById(id).orElseThrow(LibraryUserNotFoundException::new);
    }

    public List<LibraryUser> getAllLibraryUsers() {
        return libraryUserRepository.findAll();
    }
}
