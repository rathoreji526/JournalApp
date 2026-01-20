package net.engineeringdigest.journalApp.exceptions;

public class JournalEntryWithSameTitleException extends RuntimeException{
    public JournalEntryWithSameTitleException(String message){
        super(message);
    }
}
