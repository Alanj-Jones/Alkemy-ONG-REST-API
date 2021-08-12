package com.alkemy.java.exception.organization;

import com.alkemy.java.util.logger.Errors;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log
public class OrganizationException {
    private static Errors errors;

    @ExceptionHandler()
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No entity found")
    public static void notFoundEntityException(NullOrganizationException e) {
        log.info(e.getMessage()); errors.logError(e.getMessage());}
}
