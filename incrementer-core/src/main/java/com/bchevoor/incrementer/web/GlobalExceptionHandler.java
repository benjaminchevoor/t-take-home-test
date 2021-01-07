package com.bchevoor.incrementer.web;

import com.bchevoor.incrementer.auth.AuthenticationFailureException;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationFailureException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public void handleAuthException() {
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseBody handleIllegalArgException(IllegalArgumentException e) {
        return new ErrorResponseBody().setReason(e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleThrowable(Exception t) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(t.getClass(), ResponseStatus.class) != null) {
            throw t;
        }

        System.out.println("Encountered exception -- report to admin");
        t.printStackTrace();
    }

}
