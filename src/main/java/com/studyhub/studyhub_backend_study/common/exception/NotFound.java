package com.studyhub.studyhub_backend_study.common.exception;

public class NotFound extends ClientError{

    public NotFound(String message){
        this.errorCode = "Not Found";
        this.errorMessage = message;
    }
}