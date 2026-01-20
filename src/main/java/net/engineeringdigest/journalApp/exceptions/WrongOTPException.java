package net.engineeringdigest.journalApp.exceptions;

public class WrongOTPException extends RuntimeException{
    public WrongOTPException(String message){
        super(message);
    }
}
