package com.alkemy.java.exception.testimonials;

import com.alkemy.java.exception.slide.NullSlideException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log
public class TestimonialsException extends Throwable {

    @ExceptionHandler()
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No entity found")
    public static void notFoundEntityException(NullSlideException e) {
        log.info(e.getMessage());}

    @ExceptionHandler()
    @ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "Ya existe este objeto Testimonials.")
    public static void isExistsTestimonialsException(ExistsTestimonialsException e) {
        log.info(e.getMessage());}
}
