package com.example.backend.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingPrerequisitesException extends RuntimeException{
     private final List<String> missingPrereqs;
    
    public MissingPrerequisitesException(List<String> missingPrereqs) {
        super("Missing completed prerequisites: " + String.join(", ", missingPrereqs));
        this.missingPrereqs = missingPrereqs;
    }
    
    public List<String> getMissingPrereqs() {
        return missingPrereqs;
    }
}
