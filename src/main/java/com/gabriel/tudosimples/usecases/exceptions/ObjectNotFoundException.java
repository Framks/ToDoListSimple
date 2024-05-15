package com.gabriel.tudosimples.usecases.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // 404
public class ObjectNotFoundException extends EntityNotFoundException {
    public ObjectNotFoundException(String message){
        super(message);
    }
}
