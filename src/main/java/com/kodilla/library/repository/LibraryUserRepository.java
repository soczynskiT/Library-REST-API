package com.kodilla.library.repository;

import com.kodilla.library.domain.LibraryUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, Long> {

    LibraryUser save(LibraryUser libraryUser);

    @Override
    List<LibraryUser> findAll();

    Optional<LibraryUser> findById(Long id);
}
