package net.engineeringdigest.journalApp.exceptions;

import net.engineeringdigest.journalApp.model.User;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException(String message){
        super(message);
    }
}
