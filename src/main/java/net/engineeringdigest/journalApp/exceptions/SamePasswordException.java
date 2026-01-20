package net.engineeringdigest.journalApp.exceptions;

public class SamePasswordException extends RuntimeException{
    public SamePasswordException(String message){
        super(message);
    }
}
