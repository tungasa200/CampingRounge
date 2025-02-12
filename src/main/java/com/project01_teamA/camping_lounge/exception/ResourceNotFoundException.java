package com.project01_teamA.camping_lounge.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s with %s: %s not found", resource, field, value));
    }
}
