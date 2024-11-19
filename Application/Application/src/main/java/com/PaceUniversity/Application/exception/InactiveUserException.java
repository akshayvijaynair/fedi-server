package com.PaceUniversity.Application.exception;

public class InactiveUserException extends RuntimeException{

    public InactiveUserException(String message){
        super(message);
    }
}
