package com.alkemy.java.exception.slide;

import com.alkemy.java.util.logger.Errors;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log
public class SlideException {
    private static Errors errors;

    @ExceptionHandler()
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No entity found")
    public static void notFoundEntityException(NullSlideException e) {
        log.info(e.getMessage()); errors.logError(e.getMessage());}

    @ExceptionHandler()
    @ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "No entity deleted")
    public static void notDeletedSlidesException(NotDeletedSlideException e) {
        log.info(e.getMessage()); errors.logError(e.getMessage());}

}
