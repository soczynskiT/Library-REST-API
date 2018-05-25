package com.kodilla.library.service;

import com.kodilla.library.domain.LibraryUser;
import com.kodilla.library.repository.LibraryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryUsersService {

    @Autowired
    LibraryUserRepository libraryUserRepository;

    public LibraryUser saveLibraryUser(final LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    public Optional<LibraryUser> getLibraryUser(Long id) {
        return libraryUserRepository.findById(id);
    }

    public List<LibraryUser> getAllLibraryUsers() {
        return libraryUserRepository.findAll();
    }
}
