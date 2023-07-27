package com.electronic.store.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String user, String resourceName) {
        super(resourceName);

    }
}