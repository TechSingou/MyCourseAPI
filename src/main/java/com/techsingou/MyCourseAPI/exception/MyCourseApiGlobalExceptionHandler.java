package com.techsingou.MyCourseAPI.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyCourseApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CourseNotFoundException.class})
    public ResponseEntity<?> handleCourseNotFoundException(CourseNotFoundException courseNotFoundException, WebRequest request) {
        return super.handleExceptionInternal(courseNotFoundException,
                courseNotFoundException.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
