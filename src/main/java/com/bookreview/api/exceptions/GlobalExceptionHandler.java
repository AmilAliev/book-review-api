package com.bookreview.api.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorObject> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        logger.error("BookNotFoundException: {}", ex.getMessage(), ex);

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(Integer.valueOf(HttpStatus.NOT_FOUND.value()));
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorObject> handleReviewNotFoundException(ReviewNotFoundException ex, WebRequest request) {
        logger.error("ReviewNotFoundException: {}", ex.getMessage(), ex);

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(Integer.valueOf(HttpStatus.NOT_FOUND.value()));
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
}
