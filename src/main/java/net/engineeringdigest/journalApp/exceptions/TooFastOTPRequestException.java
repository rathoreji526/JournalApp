package net.engineeringdigest.journalApp.exceptions;

public class TooFastOTPRequestException extends RuntimeException{
    public TooFastOTPRequestException(String message){
        super(message);
    }
}
