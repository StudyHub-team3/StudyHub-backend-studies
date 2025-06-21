package com.studyhub.studyhub_backend_study.common.exception;

public class BadParameter extends ClientError{
    public BadParameter(String message) {
        this.errorCode = "BadParameter";
        this.errorMessage = message;
    }
}