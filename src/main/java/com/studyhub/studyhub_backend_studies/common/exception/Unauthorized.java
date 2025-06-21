package com.studyhub.studyhub_backend_studies.common.exception;

public class Unauthorized extends RuntimeException {
    public Unauthorized(String message) {
        super(message);
    }
}