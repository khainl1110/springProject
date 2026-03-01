package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyEnrolledException extends RuntimeException {
    public AlreadyEnrolledException(Long studentId, Long courseId) {
        super("Student " + studentId + " already enrolled in course " + courseId);
    }
}

