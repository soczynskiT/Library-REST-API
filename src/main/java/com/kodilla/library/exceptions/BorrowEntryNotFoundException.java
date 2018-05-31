package com.kodilla.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such a borrow entry in system")
public class BorrowEntryNotFoundException extends RuntimeException{
}
