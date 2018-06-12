package com.kodilla.library.controller;

import com.kodilla.library.domain.dtos.ExceptionDto;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ExceptionDto defaultErrorHandler(final HttpServletRequest req, final Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        return new ExceptionDto("An error occurs with details...",
                e.getClass().getSimpleName(), req.getRequestURL().toString());
    }
}

