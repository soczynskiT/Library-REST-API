package com.kodilla.library.controller;

import com.kodilla.library.exceptions.LibraryUserNotFoundException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LibraryUserNotFoundException.class)
    public ModelAndView
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation
                (e.getClass(), ResponseStatus.class) != null)
            throw e;

        ModelAndView mav = new ModelAndView();
        mav.addObject("Exception: ", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("DEFAULT_ERROR_VIEW");
        return mav;
    }
}

