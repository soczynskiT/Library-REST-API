package com.kodilla.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There are no available, requested books to borrow")
public class NoAvailableCopiesFoundedException extends RuntimeException {
}
