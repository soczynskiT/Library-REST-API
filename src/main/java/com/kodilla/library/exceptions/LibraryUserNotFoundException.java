package com.kodilla.library.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This user is not found in the system")
public class LibraryUserNotFoundException extends RuntimeException {

}
